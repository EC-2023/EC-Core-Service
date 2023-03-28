package src.service;

import jakarta.servlet.http.HttpServletRequest;
import src.config.dto.PagedResultDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IService<T, D, E> {
    public CompletableFuture<List<T>> getAll();

    public CompletableFuture<T> getOne(UUID id);

    public CompletableFuture<T> create(D input);

    public CompletableFuture<T> update(UUID id, E input);

    public CompletableFuture<PagedResultDto<T>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip);

    public CompletableFuture<Void> remove(UUID id);
}
