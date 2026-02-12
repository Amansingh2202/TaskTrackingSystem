package com.servlets.updateservlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojo.Programs;
import com.pojo.ProgramsBaseFile;
import com.pojo.Projects;
import com.pojo.Tasks;
import com.util.DataSaver;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@WebServlet("/updateTaskStatus")
public class UpdateProjectStatus  extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            String taskId = request.getParameter("taskId");
            String newStatus = request.getParameter("status");

            if (taskId == null || newStatus == null) {
                response.setStatus(400);
                response.getWriter().write("taskId and status required");
                return;
            }

            if (GlobalData.Data == null) {
                response.setStatus(400);
                response.getWriter().write("Data not initialized");
                return;
            }

            boolean check = false;

            ProgramsBaseFile data = GlobalData.Data;
            List<Programs> programsList = data.getPrograms();

            for (int i = 0; i < programsList.size(); i++) {

                Programs programs = programsList.get(i);
                List<Projects> projectsList = programs.getProjects();

                for (int j = 0; j < projectsList.size(); j++) {

                    Projects projects = projectsList.get(j);
                    List<Tasks> taskList = projects.getTasks();

                    for (int k = 0; k < taskList.size(); k++) {

                        Tasks task = taskList.get(k);

                        if (taskId.equals(task.getTaskId())) {
                            task.setStatus(newStatus);
                            check = true;
                            break;
                        }
                    }
                }
            }

            if (!check) {
                response.setStatus(404);
                response.getWriter().write("Task not found");
                return;
            }


            DataSaver.saveToFile();

            response.setStatus(200);
            response.getWriter().write("Status updated");

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write(e.getMessage());
        }
    }
}
