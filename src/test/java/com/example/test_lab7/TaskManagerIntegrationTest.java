package com.example.test_lab7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskManagerIntegrationTest {
    
    private TaskManager taskManager;
    
    @BeforeEach
    public void setUp() {
        taskManager = new TaskManager();
        System.out.println("=== НАЧАЛО ИНТЕГРАЦИОННОГО ТЕСТА ===");
    }
    
    @Test
    @DisplayName("Интеграционный тест: полный жизненный цикл задачи")
    public void testCompleteTaskLifecycle() {
        System.out.println("Тест 1: Полный жизненный цикл задачи");
        
        // Создание задачи
        Task task = taskManager.createTask("Написать код", "Реализовать фичу X", Priority.HIGH);
        assertNotNull(task, "Задача должна быть создана");
        assertEquals("Написать код", task.getTitle());
        assertEquals(Status.TODO, task.getStatus());
        
        // Получение задачи
        Task retrieved = taskManager.getTask(task.getId());
        assertEquals(task.getId(), retrieved.getId());
        
        // Изменение статуса
        assertTrue(taskManager.updateTaskStatus(task.getId(), Status.IN_PROGRESS));
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(task.getId()).getStatus());
        
        // Завершение задачи
        assertTrue(taskManager.updateTaskStatus(task.getId(), Status.DONE));
        assertEquals(Status.DONE, taskManager.getTask(task.getId()).getStatus());
        
        // Проверка количества завершенных задач
        assertEquals(1, taskManager.getCompletedTasksCount());
        
        System.out.println("✓ Жизненный цикл задачи завершен успешно");
    }
    
    @Test
    @DisplayName("Интеграционный тест: обработка нескольких задач")
    public void testMultipleTasksProcessing() {
        System.out.println("Тест 2: Обработка нескольких задач");
        
        // Создание задач с разными приоритетами
        taskManager.createTask("Важная задача", "Срочно!", Priority.HIGH);
        taskManager.createTask("Обычная задача", "Можно позже", Priority.MEDIUM);
        taskManager.createTask("Низкий приоритет", "Когда будет время", Priority.LOW);
        
        // Проверка общего количества
        assertEquals(3, taskManager.getAllTasks().size());
        
        // Проверка фильтрации по приоритету
        assertEquals(1, taskManager.getTasksByPriority(Priority.HIGH).size());
        assertEquals(1, taskManager.getTasksByPriority(Priority.MEDIUM).size());
        assertEquals(1, taskManager.getTasksByPriority(Priority.LOW).size());
        
        System.out.println("✓ Обработка нескольких задач завершена");
    }
    
    @Test
    @DisplayName("Интеграционный тест: граничные случаи и валидация")
    public void testBoundaryCasesAndValidation() {
        System.out.println("Тест 3: Граничные случаи и валидация");
        
        // Проверка на пустой заголовок
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createTask("", "Описание", Priority.MEDIUM);
        });
        assertTrue(exception.getMessage().contains("не может быть пустым"));
        
        // Проверка обновления несуществующей задачи
        assertFalse(taskManager.updateTaskStatus(999, Status.DONE));
        
        System.out.println("✓ Граничные случаи обработаны корректно");
    }
    
    @Test
    @DisplayName("Интеграционный тест: статусы и приоритеты")
    public void testStatusesAndPriorities() {
        System.out.println("Тест 4: Статусы и приоритеты");
        
        // Создаем задачи
        Task highPriority = taskManager.createTask("Важная", "", Priority.HIGH);
        Task mediumPriority = taskManager.createTask("Средняя", "", Priority.MEDIUM);
        Task lowPriority = taskManager.createTask("Несрочная", "", Priority.LOW);
        
        // Меняем статусы
        taskManager.updateTaskStatus(highPriority.getId(), Status.DONE);
        taskManager.updateTaskStatus(mediumPriority.getId(), Status.IN_PROGRESS);
        
        // Проверяем статистику
        assertEquals(1, taskManager.getCompletedTasksCount());
        assertEquals(1, taskManager.getTasksByPriority(Priority.HIGH).size());
        
        // Проверяем, что низкоприоритетная задача осталась TODO
        assertEquals(Status.TODO, taskManager.getTask(lowPriority.getId()).getStatus());
        
        System.out.println("✓ Статусы и приоритеты работают корректно");
    }
}
