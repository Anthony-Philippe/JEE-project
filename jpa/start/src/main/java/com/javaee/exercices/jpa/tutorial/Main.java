package com.javaee.exercices.jpa.tutorial;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class Main {
    private static final Logger LOGGER = Logger.getLogger("JPA");

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        EntityManagerFactory factory = null;
        EntityManager entityManager = null;
        try {
            factory =
                    Persistence.createEntityManagerFactory("PersistenceUnit");
            entityManager = factory.createEntityManager();
            persistPerson(entityManager);
            persistGeek(entityManager);
            addPhones(entityManager);
            listPersons(entityManager);
            createProject(entityManager);
            listProjects(entityManager);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }

    private void persistPerson(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Person person = new Person();
            person.setFirstName("Homer");
            person.setLastName("Simpson");
            entityManager.persist(person);
            IdCard idCard = new IdCard();
            idCard.setIdNumber("4711");
            idCard.setIssueDate(new Date());
            person.setIdCard(idCard);
            entityManager.persist(idCard);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private void persistGeek(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Geek geek = new Geek();
            geek.setFirstName("Gavin");
            geek.setLastName("Coffee");
            geek.setFavouriteProgrammingLanguage("Java");
            entityManager.persist(geek);
            geek = new Geek();
            geek.setFirstName("Thomas");
            geek.setLastName("Micro");
            geek.setFavouriteProgrammingLanguage("C#");
            entityManager.persist(geek);
            geek = new Geek();
            geek.setFirstName("Christian");
            geek.setLastName("Cup");
            geek.setFavouriteProgrammingLanguage("Java");
            entityManager.persist(geek);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private void listPersons(EntityManager entityManager) {
        TypedQuery<Person> query = entityManager.createQuery("from Person p left\n" +
                "join fetch p.phones", Person.class);
        List<Person> resultList = query.getResultList();
        for (Person person : resultList) {
            StringBuilder sb = new StringBuilder();
            sb.append(person.getFirstName()).append("").append(person.getLastName());
            if (person instanceof Geek) {
                Geek geek = (Geek) person;
                sb.append(" ").append(geek.getFavouriteProgrammingLanguage());
            }
            IdCard idCard = person.getIdCard();
            if (idCard != null) {
                sb.append(" ").append(idCard.getIdNumber()).append("").append(idCard.getIssueDate());
            }
            List<Phone> phones = person.getPhones();
            for (Phone phone : phones) {
                sb.append(" ").append(phone.getNumber());
            }
            LOGGER.info(sb.toString());
        }
    }

    private void addPhones(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Person> query = builder.createQuery(Person.class);
            Root<Person> personRoot = query.from(Person.class);
            query.where(builder.and(
                    builder.equal(personRoot.get("firstName"), "Homer"),
                    builder.equal(personRoot.get("lastName"), "Simpson")));
            List<Person> resultList =
                    entityManager.createQuery(query).getResultList();
            for (Person person : resultList) {
                Phone phone = new Phone();
                phone.setNumber("+49 1234 456789");
                entityManager.persist(phone);
                person.getPhones().add(phone);
                phone.setPerson(person);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private void createProject(EntityManager entityManager) {
        List<Geek> resultList = entityManager.createQuery("from Geek where favouriteProgrammingLanguage = :fpl ", Geek.class)
                .setParameter("fpl", "Java").getResultList();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Project project = new Project();
        project.setTitle("Java Project");
        Period period = new Period();
        period.setStartDate(createDate(1, 1, 2017));
        period.setEndDate(createDate(31, 12, 2017));
        project.setProjectPeriod(period);
        project.setProjectType(Project.ProjectType.TIME_AND_MATERIAL);
        for (Geek geek : resultList) {
            project.getGeeks().add(geek);
            geek.getProjects().add(project);
        }
        entityManager.persist(project);
        transaction.commit();
    }

    private Date createDate(int day, int month, int year) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DAY_OF_MONTH, day);
        gc.set(Calendar.MONTH, month - 1);
        gc.set(Calendar.YEAR, year);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return new Date(gc.getTimeInMillis());
    }

    private void listProjects(EntityManager entityManager) {
        TypedQuery<Project> query = entityManager.createQuery("from Project p where p.projectPeriod.startDate = :startDate", Project.class)
                .setParameter("startDate", createDate(1, 1, 2017));
        List<Project> resultList = query.getResultList();
        for (Project project : resultList) {
            StringBuilder sb = new StringBuilder();
            sb.append(project.getTitle());
            List<Geek> geeks = project.getGeeks();
            for (Geek geek : geeks) {
                sb.append(" ").append(geek.getFirstName()).append("").append(geek.getLastName());
            }
            LOGGER.info(sb.toString());
        }
    }

}