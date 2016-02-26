package test;

import entity.Student;
import entity.Studypoint;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Tester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        
        List<Student> allStudents = em.createNamedQuery("Student.findAll", Student.class).getResultList();
        List<Student> janStudents = em.createNamedQuery("Student.findByFirstname", Student.class).setParameter("firstname", "jan").getResultList();
        List<Student> olsenStudents = em.createNamedQuery("Student.findByLastname", Student.class).setParameter("lastname", "olsen").getResultList();
        
        
        Student stud = em.createNamedQuery("Student.findById", Student.class).setParameter("id", 1).getSingleResult();
        Long studGradeSum = em.createQuery("SELECT sum(sp.score) FROM Studypoint sp WHERE sp.studentId = :student", Long.class).setParameter("student", stud).getSingleResult();
        
        Long gradeSum = em.createQuery("SELECT sum(sp.score) FROM Studypoint sp", Long.class).getSingleResult();
        
        // SQL Statement som finder de ønskede student ids
        //SELECT sp.STUDENT_ID, SUM(sp.score) AS totalScore FROM studypoint sp GROUP BY sp.STUDENT_ID HAVING totalScore = (SELECT MAX(spStudGroup.totalScore) FROM (SELECT SUM(sp.score) AS totalScore, sp.STUDENT_ID FROM studypoint sp GROUP BY sp.STUDENT_ID) spStudGroup);
        // Uden score
        //SELECT sp.STUDENT_ID FROM studypoint sp GROUP BY sp.STUDENT_ID HAVING SUM(sp.score) = (SELECT MAX(spStudGroup.totalScore) FROM (SELECT SUM(sp.score) AS totalScore, sp.STUDENT_ID FROM studypoint sp GROUP BY sp.STUDENT_ID) spStudGroup);
        
        // SQL som finder studerende med højeste score
        // SELECT * FROM STUDENT s WHERE s.id in (SELECT sp.STUDENT_ID FROM studypoint sp GROUP BY sp.STUDENT_ID HAVING SUM(sp.score) = (SELECT MAX(spStudGroup.totalScore) FROM (SELECT SUM(sp.score) AS totalScore, sp.STUDENT_ID FROM studypoint sp GROUP BY sp.STUDENT_ID) spStudGroup));
        
        // Jeg har ingen anelse om hvordan jeg får løst denne opgave der er nok gået 3 timer nu og pas.
        
        // SELECT s FROM Student s WHERE (SELECT count(p.score) FROM Studypoint p)=(SELECT MIN(SELECT COUNT(sss.score ) FROM Studypoint sss ) FROM Studypoint ss)
        List<Student> highestGradeSum = em.createQuery("SELECT s FROM Student s WHERE s in (SELECT p1.studentId from Studypoint p1 group by p1.studentId having SUM(p1.score) >= all (select SUM(p2.score) from Studypoint p2 group by p2.studentId ))", Student.class).getResultList();
        List<Student> lowestGradeSum = em.createQuery("SELECT s FROM Student s WHERE s in (SELECT p1.studentId from Studypoint p1 group by p1.studentId having SUM(p1.score) <= all (select SUM(p2.score) from Studypoint p2 group by p2.studentId ))", Student.class).getResultList();
        System.out.println("Find all Students in the system:");
        for (Student student : allStudents) {
            System.out.println("Name: " + student.getFirstname());
        }
        
        System.out.println("Find all Students in the System with the first Name jan:");
        for (Student student : janStudents) {
            System.out.println("Name: " + student.getFirstname());
        }
        
        System.out.println("Find all Students in the system with the last name Olsen:");
        for (Student student : olsenStudents) {
            System.out.println("Name: " + student.getFirstname());
        }
        
        System.out.println("Find the total sum of study point scores, for a student given the student id:");
        System.out.println(studGradeSum);
        
        System.out.println("Find the total sum of studypoint scores, given to all students:");
        System.out.println(gradeSum);
        
        System.out.println("Find those students that has the greatest total of studypoint scores:");
        for (Student student : highestGradeSum) {
            System.out.println("Name: " + student.getFirstname());
        }
        
        System.out.println("Find those students that has the lowest total of studypoint scores:");
        for (Student student : lowestGradeSum) {
            System.out.println("Name: " + student.getFirstname());
        }
        
        System.out.println("Create a method to create new Students and test the method:");
        
        Student newStud = createStudent();
        System.out.println("Student ID: " + newStud.getId());
        
        System.out.println("Add a method to the Student class addStudyPoint(..) and test the method:");
        
        Studypoint sp1 = new Studypoint();
        newStud.addStudyPoint(sp1);
        
        try {
            em.getTransaction().begin();
            em.persist(sp1);
            em.persist(newStud);
            em.getTransaction().commit();
        } finally {
            em.close();
        }        
        System.out.println("New studypoint id: " + newStud.getStudypointCollection());
        
        
    }
    
    public static Student createStudent() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        
        Student stud = new Student();
        
        try {
        em.getTransaction().begin();
        em.persist(stud);
        em.getTransaction().commit();
        } finally {
        em.close();
        }
        
        return stud;
        
    }
    
}
