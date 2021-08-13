package com.assignment.spring.external.storage;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "weather")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String city;

    private String country;

    private Double temperature;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeatherEntity that = (WeatherEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        // as we rely on id generated on database side, and we are not going to put that entity into a set, hashcode can
        // have a constant value. We only check equality on id that is null until saved
        return 932649044;
    }
}
