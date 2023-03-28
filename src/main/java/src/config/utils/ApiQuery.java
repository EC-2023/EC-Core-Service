package src.config.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiQuery<T> {
    CriteriaBuilder cb;
    CriteriaQuery<T> cq;
    HttpServletRequest req;
    Root<T> root;
    EntityManager em;
    List<Predicate> predicates = new ArrayList<>();
    private TypedQuery<T> query = null;


    public ApiQuery(HttpServletRequest req, EntityManager em, Class<T> entityType) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();
        this.cq = cb.createQuery(entityType);
        this.req = req;
        this.root = cq.from(entityType);
    }

    public ApiQuery<T> filter() {
        predicates.add(cb.equal(root.get("isDeleted"), false));
        String queryString = null;
        try {
            queryString = URLDecoder.decode(req.getQueryString(),  "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        if (queryString != null) {
            Pattern pattern = Pattern.compile("(?i)(\\w+)\\{\\{(lt|lte|gt|gte|search)\\}\\}=(.*?)(&|$)");
            Matcher matcher = pattern.matcher(queryString);
            while (matcher.find()) {
                switch (matcher.group(2)) {
                    case "lt": {
                        predicates.add(cb.lt(root.get(matcher.group(1)), Integer.parseInt(matcher.group(3))));
                        break;
                    }
                    case "lte": {
                        predicates.add(cb.lessThanOrEqualTo(root.get(matcher.group(1)), Integer.parseInt(matcher.group(3))));
                        break;
                    }
                    case "gt": {
                        predicates.add(cb.gt(root.get(matcher.group(1)), Integer.parseInt(matcher.group(3))));
                        break;
                    }
                    case "gte": {
                        predicates.add(cb.greaterThanOrEqualTo(root.get(matcher.group(1)), Integer.parseInt(matcher.group(3))));
                        break;
                    }
                    case "search": {
                        predicates.add(cb.like(root.get(matcher.group(1)), "%" + matcher.group(3).toLowerCase()  + "%"));
                        break;
                    }
                }

            }
        }
        if (predicates.size() > 0)
            cq.where(predicates.toArray(new Predicate[0]));
        return this;
    }

    public ApiQuery<T> paginate() {
        int skip = req.getParameter("skip") != null ? Integer.parseInt(req.getParameter("skip")) : 0;
        int limit = req.getParameter("limit") != null ? Integer.parseInt(req.getParameter("limit")) : 10;
        this.query = em.createQuery(cq).setFirstResult(skip).setMaxResults((skip + 1) * limit);
        return this;
    }

    public ApiQuery<T> orderBy() {
        if (req.getParameter("orderBy") != null) {
            List<Order> orders = Arrays.stream(req.getParameter("orderBy").split("\\s*,\\s*")).toList().stream().map(x -> {
                return !x.contains("-") ? cb.asc(root.get(x)) : cb.desc(root.get(x.substring(1)));
            }).toList();
            cq.orderBy(orders);
        }
        return this;
    }

    public List<T> exec() {

        if (this.query == null)
            return em.createQuery(cq).getResultList();
        else
            return query.getResultList();
    }
}
