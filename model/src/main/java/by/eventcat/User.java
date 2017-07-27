package by.eventcat;

import java.util.Objects;

/**
 * User model
 */
public class User {

    private long userId;

    private String userName;

    private String userPassword;

    private String userRequestData;

    private String userEmail;

    private String userPhoneNumber;

    private String userServicePlan;

    private Float userBalanсe;

    private String Role;

    private String userPermissions;

    private boolean isEnabled;

    public User() {
    }

    public User(long userId, String userName, String userPassword, String userRequestData, String userEmail,
                String userPhoneNumber, String userServicePlan, Float userBalanсe, String role, String userPermissions, boolean isEnabled) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRequestData = userRequestData;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userServicePlan = userServicePlan;
        this.userBalanсe = userBalanсe;
        Role = role;
        this.userPermissions = userPermissions;
        this.isEnabled = isEnabled;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRequestData() {
        return userRequestData;
    }

    public void setUserRequestData(String userRequestData) {
        this.userRequestData = userRequestData;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public Float getUserBalanсe() {
        return userBalanсe;
    }

    public void setUserBalanсe(Float userBalanсe) {
        this.userBalanсe = userBalanсe;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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
                Objects.equals(userName, user.userName) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userRequestData, user.userRequestData) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userPhoneNumber, user.userPhoneNumber) &&
                Objects.equals(userServicePlan, user.userServicePlan) &&
                Objects.equals(userBalanсe, user.userBalanсe) &&
                Objects.equals(Role, user.Role) &&
                Objects.equals(userPermissions, user.userPermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPassword, userRequestData, userEmail, userPhoneNumber,
                userServicePlan, userBalanсe, Role, userPermissions, isEnabled);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRequestData='" + userRequestData + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userServicePlan='" + userServicePlan + '\'' +
                ", userBalanсe=" + userBalanсe +
                ", Role='" + Role + '\'' +
                ", userPermissions='" + userPermissions + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
