/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author matskruger
 */
@Entity
@Table(name = "studypoint")
@NamedQueries({
    @NamedQuery(name = "Studypoint.findAll", query = "SELECT s FROM Studypoint s"),
    @NamedQuery(name = "Studypoint.findById", query = "SELECT s FROM Studypoint s WHERE s.id = :id"),
    @NamedQuery(name = "Studypoint.findByDescription", query = "SELECT s FROM Studypoint s WHERE s.description = :description"),
    @NamedQuery(name = "Studypoint.findByMaxval", query = "SELECT s FROM Studypoint s WHERE s.maxval = :maxval"),
    @NamedQuery(name = "Studypoint.findByScore", query = "SELECT s FROM Studypoint s WHERE s.score = :score")})
public class Studypoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MAXVAL")
    private Integer maxval;
    @Column(name = "SCORE")
    private Integer score;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Student studentId;

    public Studypoint() {
    }

    public Studypoint(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxval() {
        return maxval;
    }

    public void setMaxval(Integer maxval) {
        this.maxval = maxval;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studypoint)) {
            return false;
        }
        Studypoint other = (Studypoint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Studypoint[ id=" + id + " ]";
    }
    
}
