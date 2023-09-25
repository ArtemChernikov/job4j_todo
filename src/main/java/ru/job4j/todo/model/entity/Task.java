package ru.job4j.todo.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 19.06.2023
 */
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {

    @EqualsAndHashCode.Include
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean done;
}
