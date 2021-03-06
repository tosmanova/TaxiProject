package ua.taxi.base.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by andrii on 09.07.16.
 */

@MappedSuperclass
public abstract class IdAutoGeneratedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IdAutoGeneratedEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdAutoGeneratedEntity that = (IdAutoGeneratedEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
