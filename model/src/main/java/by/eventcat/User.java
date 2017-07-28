package by.eventcat;

import javax.persistence.*;
import java.util.*;

/**
 * User model
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userId;

    //main user identifier - unique field
    @Column(name = "user_email", unique = true)
    private String userEmail;

    @Transient
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    private String role;

    //name-surname or nick - how user wants to be named
    @Column(name = "user_name")
    private String userName;

    @ManyToMany
    @JoinTable(name = "users_locations_correlation",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "location_id")})
    private List<Locality> localities = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "users_event_place_correlation",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_place_id")})
    private List<EventPlace> placesAvailable = new ArrayList<>();

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_service_plan")
    private String userServicePlan;

    @Column(name = "user_balance")
    private Float userBalance;

    @Column(name = "user_permissions")
    private String userPermissions;

    @Column(name = "user_is_enabled")
    private boolean isEnabled;

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Locality> getLocalities() {
        return localities;
    }

    public void setLocalities(List<Locality> localities) {
        this.localities = localities;
    }

    public List<EventPlace> getPlacesAvailable() {
        return placesAvailable;
    }

    public void setPlacesAvailable(List<EventPlace> placesAvailable) {
        this.placesAvailable = placesAvailable;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserServicePlan() {
        return userServicePlan;
    }

    public void setUserServicePlan(String userServicePlan) {
        this.userServicePlan = userServicePlan;
    }

    public Float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Float userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(String userPermissions) {
        this.userPermissions = userPermissions;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                isEnabled == user.isEnabled &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(role, user.role) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(localities, user.localities) &&
                Objects.equals(placesAvailable, user.placesAvailable) &&
                Objects.equals(userPhoneNumber, user.userPhoneNumber) &&
                Objects.equals(userServicePlan, user.userServicePlan) &&
                Objects.equals(userBalance, user.userBalance) &&
                Objects.equals(userPermissions, user.userPermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userPassword, role, userName, localities, placesAvailable,
                userPhoneNumber, userServicePlan, userBalance, userPermissions, isEnabled);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                //", localities=" + localities +
                //", placesAvailable=" + placesAvailable +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userServicePlan='" + userServicePlan + '\'' +
                ", userBalance=" + userBalance +
                ", userPermissions='" + userPermissions + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}