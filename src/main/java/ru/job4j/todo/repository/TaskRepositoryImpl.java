package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.entity.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 19.06.2023
 */
@Slf4j
@AllArgsConstructor
@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> create(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Task newTask) {
        try {
            crudRepository.run(session -> session.update(newTask));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer taskId) {
        return crudRepository.bool("DELETE Task WHERE id = :id", Map.of("id", taskId));
    }

    @Override
    public void deleteAll() {
        crudRepository.run("DELETE FROM Task");
    }

    @Override
    public boolean completeTask(Integer id) {
        return crudRepository.bool("UPDATE FROM Task SET done = true WHERE id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Task> findById(Integer taskId) {
        return crudRepository
                .optional("FROM Task f JOIN FETCH f.priority JOIN FETCH f.categories WHERE f.id = :id",
                        Task.class, Map.of("id", taskId));
    }

    @Override
    public List<Task> findAll() {
        return crudRepository
                .list("SELECT DISTINCT f FROM Task f JOIN FETCH f.priority JOIN FETCH f.categories", Task.class);
    }

    @Override
    public List<Task> findTasksByDone(boolean done) {
        return crudRepository
                .list("SELECT DISTINCT f FROM Task f JOIN FETCH f.priority JOIN FETCH f.categories WHERE done = :done",
                        Task.class, Map.of("done", done));
    }

}
