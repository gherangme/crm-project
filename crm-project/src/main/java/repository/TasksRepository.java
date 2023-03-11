package repository;

import config.MysqlConfig;
import model.TaskModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TasksRepository {

    public List<TaskModel> getAllTasks() {
        List<TaskModel> list = new ArrayList<>();
        Connection connection = MysqlConfig.getConnection();
        String query = "select * from tasks";
        String query_user_id = "select * from users u where u.id = ?";
        String query_job_id = "select * from jobs u where u.id = ?";
        String query_status_id = "select * from status u where u.id = ?";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();

            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setTaskName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getDate("start_date"));
                taskModel.setEndDate(resultSet.getDate("end_date"));
                taskModel.setUser_id(resultSet.getInt("user_id"));

                PreparedStatement statement1 = connection.prepareStatement(query_user_id);
                statement1.setInt(1,taskModel.getUser_id());
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()) {
                    taskModel.setUser(resultSet1.getString("fullname"));
                }

                PreparedStatement statement2 = connection.prepareStatement(query_job_id);
                statement2.setInt(1,resultSet.getInt("job_id"));
                ResultSet resultSet2 = statement2.executeQuery();
                while (resultSet2.next()) {
                    taskModel.setProjectName(resultSet2.getString("name"));
                }

                PreparedStatement statement3 = connection.prepareStatement(query_status_id);
                statement3.setInt(1,resultSet.getInt("status_id"));
                ResultSet resultSet3 = statement3.executeQuery();
                while (resultSet3.next()) {
                    taskModel.setStatus(resultSet3.getString("name"));
                }

                list.add(taskModel);
            }
        }catch (Exception e) {
            System.out.println("Lỗi câu truy vấn getAllTask "+e.getMessage());
        }finally {
            try {
                connection.close();
            }catch (Exception e) {

            }
        }

        return list;
    }

    public int addNewTask(String name, Date start_date, Date end_date,int user_id,String projectName) {
        int isSuccess = 0;
        Connection connection = MysqlConfig.getConnection();
        String queryProjectName = "select * from jobs u where u.name = ?";
        String queryUserName = "select * from user u where u.id = ?";
        String query = "insert into tasks(name,start_date,end_date,user_id,job_id,status_id) value(?,?,?,?,?,1);";

        try {
            PreparedStatement statement = connection.prepareStatement(queryProjectName);
            statement.setString(1,projectName);
            ResultSet resultSet = statement.executeQuery();

//            Chỉ query 1 lần vì mỗi project chỉ có 1 tên
            while (resultSet.next()) {
                int idProjectName = resultSet.getInt("id");
                statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setDate(2, start_date);
                statement.setDate(3, end_date);
                statement.setInt(4, user_id);
                statement.setInt(5, idProjectName);

//                PreparedStatement statement1 = connection.prepareStatement(queryUserName)
//                statement1.setInt(1,user_id);
//                ResultSet resultSet1 = statement1.executeQuery();
//                while (resultSet1.next()) {
//                    getAllTasks();
//                }
            }

            isSuccess = statement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Lỗi câu query addNewTask "+e.getMessage());
        }finally {
            try {
                connection.close();
            }catch (Exception e) {

            }
        }

        return isSuccess;
    }

    public int deleteTaskById(int id) {
        int isDeleteSuccess = 0;
        Connection connection = MysqlConfig.getConnection();
        String query = "delete from tasks u where u.id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            isDeleteSuccess = statement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error deleteTaskById"+e.getMessage());
        }finally {
            try {
                connection.close();
            }catch (Exception e) {

            }
        }

        return isDeleteSuccess;
    }

}
