package com.azakharov.employeeapp.repository.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private EmployeePositionEntity positionEntity;

    protected EmployeeEntity() { }

    public EmployeeEntity(final Long id,
                          final String firstName,
                          final String surname,
                          final EmployeePositionEntity positionEntity) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.positionEntity = positionEntity;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public EmployeePositionEntity getPositionEntity() {
        return positionEntity;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", surname='" + surname + '\'' +
               ", positionEntity=" + positionEntity +
               '}';
    }
}