package com.mycompany.interviewtask.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Рейтинг решил вынести в базу, так как в будущем вохможно расширение и в случае чего, можно без хардкода доабвить.
 * Плюс помогает обрабатывать потребителей независимо от доабвления рейтингв, так как в сервисе я их просто вытаскиваю все
 * и использую.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    private Integer id;
    private String name;
    private int value;
}
