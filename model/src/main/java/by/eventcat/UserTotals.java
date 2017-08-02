package by.eventcat;

import javax.persistence.*;
import java.util.Objects;

/**
 * Model to store aggregate data (counts) about registered users (admins)
 */
@Entity
@Table(name = "user_totals")
public class UserTotals {

    @Id
    @Column(name = "user_totals_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //for cityAdmin && localAdmin
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locality city;

    @Column(name = "count")
    private int count;

    public UserTotals() {
    }

    public UserTotals(UserRole userRole, Locality city, int count) {
        this.userRole = userRole;
        this.city = city;
        this.count = count;
    }

    //    //for SUPER_ADMIN
//    public UserTotals(UserRole UserRole, int count) {
//        this.UserRole = UserRole;
//        this.count = count;
//    }
//
//    //for CITY_ADMIN and LOCAL_ADMIN
//    public UserTotals(UserRole UserRole, Locality city, int count) {
//        this.UserRole = UserRole;
//        this.city = city;
//        this.count = count;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Locality getCity() {
        return city;
    }

    public void setCity(Locality city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTotals that = (UserTotals) o;
        return id == that.id &&
                count == that.count &&
                userRole == that.userRole &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userRole, city, count);
    }

    @Override
    public String toString() {
        return "UserTotals{" +
                "id=" + id +
                ", UserRole=" + userRole +
                ", city=" + city +
                ", count=" + count +
                '}';
    }
}
