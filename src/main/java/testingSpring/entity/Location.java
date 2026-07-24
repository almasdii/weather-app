package testingSpring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "weather_location",schema = "weather_schema")
@Entity
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(updatable = false,nullable = false)
    private BigDecimal latitube;
    @Column(updatable = false,nullable = false)
    private BigDecimal longitube;

    @Column(updatable = false,nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public Location(String name, User user,  BigDecimal latitube ,BigDecimal longitube,String country) {
        this.name = name;
        this.user = user;
        this.latitube = latitube;
        this.longitube = longitube;
        this.country = country;
    }
}
