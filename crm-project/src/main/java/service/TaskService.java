package service;

import model.TaskModel;
import repository.TasksRepository;
import repository.UsersRepository;

import java.sql.Date;
import java.util.List;

public class TaskService {
    public List<TaskModel> getAllTasks() {
        TasksRepository tasksRepository = new TasksRepository();
        return tasksRepository.getAllTasks();
    }

    public boolean addNewTask(String name, Date start_date, Date end_date, int user_id, String projectName) {
        TasksRepository tasksRepository = new TasksRepository();

        return tasksRepository.addNewTask(name,start_date,end_date,user_id,projectName)>=1;
    }

    public boolean deleteTaskById(int id) {
        TasksRepository tasksRepository = new TasksRepository();
        return tasksRepository.deleteTaskById(id)>=1;
    }
}
