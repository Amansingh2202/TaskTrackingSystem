package com.servlets.createservlets;

import com.pojo.Programs;
import com.pojo.Projects;
import com.pojo.Tasks;
import com.util.DataSaver;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/createTask")
public class CreateTask extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {

            String projectId = req.getParameter("projectId");
            String taskId = req.getParameter("taskId");
            String taskName = req.getParameter("taskName");
            String ownerId = req.getParameter("ownerId");
            if (projectId == null || projectId.isEmpty()|| taskId == null || taskId.isEmpty()||
                    taskName == null || taskName.isEmpty()||ownerId==null||ownerId.isEmpty()) {
                res.setStatus(400);
                res.getWriter().write(" parameters required");
                return;
            }
            boolean check=false;

            Tasks t = new Tasks();
            t.setTaskId(taskId);
            t.setTaskName(taskName);
            t.setOwnerId(ownerId);
            t.setStatus("PENDING");

            List<Programs> programs = GlobalData.Data.getPrograms();

            for (int i = 0; i < programs.size(); i++) {

                List<Projects> projects = programs.get(i).getProjects();

                for (int j = 0; j < projects.size(); j++) {

                    if (projectId.equals(projects.get(j).getProjectId())) {
                        projects.get(j).getTasks().add(t);
                        check=true;
                    }
                }
            }
            if(!check)
            {
                res.setStatus(400);
                res.getWriter().write("Wrong Input Fields");
            }


            DataSaver.saveToFile();
            res.getWriter().write("Task created");

        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write(e.getMessage());
        }
    }
}
