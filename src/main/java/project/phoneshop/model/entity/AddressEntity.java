package project.phoneshop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.annotation.Nullable;
import javax.persistence.*;

@RestResource(exported = false)
@Entity
@Table(name = "\"addresses\"")
public class AddressEntity {
    @Id
    @GeneratedValue(
            generator = "UUID"
    )
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "\"id\"")
    private String id;
    @Column(name = "\"full_name\"")
    private String fullName;
    @Basic
    @Column(name="\"company_name\"")
    private String companyName;
    @Column(name = "\"phone_number\"")
    private String phoneNumber;
    @Column(name="\"province\"")
    private String province;
    @Column(name="\"district\"")
    private String district;
    @Column(name="\"commune\"")
    private String commune;
    @Column(name="\"address_detail\"")
    private String addressDetail;
    @ManyToOne
    @JoinColumn(name="\"address_type\"",columnDefinition = "integer")
    private AddressTypeEntity addressType;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "\"user\"")
    private UserEntity user;

    public AddressEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public AddressTypeEntity getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeEntity addressType) {
        this.addressType = addressType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }
}
