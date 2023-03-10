package src.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_address", catalog = "")
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

    public Boolean getDelete() {
        return isDeleted;
    }

    public void setDeleted(Boolean delete) {
        isDeleted = delete;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

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

    public UUID getAddressId() {
        return Id;
    }

    public void setAddressId(UUID addressId) {
        this.Id = addressId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNameRecipient() {
        return nameRecipient;
    }

    public void setNameRecipient(String nameRecipient) {
        this.nameRecipient = nameRecipient;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddress that = (UserAddress) o;
        return Id == that.Id && userId == that.userId && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(ward, that.ward)
                && Objects.equals(district, that.district) && Objects.equals(zipcode, that.zipcode) && Objects.equals(numberPhone, that.numberPhone)
                && Objects.equals(nameRecipient, that.nameRecipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, userId, country, city, ward, district, zipcode, numberPhone, nameRecipient, createAt, updateAt, isDeleted);
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
