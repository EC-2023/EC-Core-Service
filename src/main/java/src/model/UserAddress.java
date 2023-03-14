package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_address")
@Data
public class UserAddress {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "address_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Basic
    @Column(name = "country", nullable = false, length = 255)
    private String country;
    @Basic
    @Column(name = "city", nullable = false, length = 255)
    private String city = "Viet Nam";
    @Basic
    @Column(name = "district", nullable = false, length = 255)
    private String district;
    @Basic
    @Column(name = "ward", nullable = false, length = 255)
    private String ward;
    @Basic
    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;
    @Basic
    @Column(name = "number_phone", nullable = false, length = 10)
    private String numberPhone;
    @Basic
    @Column(name = "name_recipient", nullable = false, length = 50)
    private String nameRecipient;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = true)
    private Date createAt = new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt = new Date(new java.util.Date().getTime());
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
}
