package com.servlets;

import com.google.gson.Gson;
import com.pojo.*;
import com.util.GlobalData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This servlet is to find all the programs where a given Individual is enrolled in

@WebServlet("/individualPrograms")

public class IndividualInrolledPrograms extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            String requestIndividualId = request.getParameter("userId");


            boolean check=false;


            if (requestIndividualId == null || requestIndividualId.isEmpty()) {
                response.setStatus(400);
                response.getWriter().write("userId parameter required");
                return;
            }
            if (GlobalData.Data == null) {
                response.setStatus(400);
                response.getWriter().write("Data not initialized");
                return;
            }

            Set<String> endividualsName = new HashSet<>();


            ProgramsBaseFile data = GlobalData.Data;
            List<Programs> programsList = data.getPrograms();

            for (int i = 0; i < programsList.size(); i++) {

                Programs programs = programsList.get(i);
                List<Projects> projectsList = programs.getProjects();

                for (int j = 0; j < projectsList.size(); j++) {

                    Projects projects = projectsList.get(j);
                    List<EnrolledIndividuals> enrolledIndividualsList =
                            projects.getEnrolledIndividuals();

                    for (int k = 0; k < enrolledIndividualsList.size(); k++) {

                        EnrolledIndividuals enrolledIndividuals =
                                enrolledIndividualsList.get(k);

                        if (requestIndividualId.equals(
                                enrolledIndividuals.getUserId())) {

                            //  add program name (requirement)
                            endividualsName.add(programs.getProgramName());
                            check=true;
                            break; // avoid duplicates from same program


                        }
                    }
                }
            }
            if(!check)
            {
                response.setStatus(400);
                response.getWriter().write("Wrong UserId");
                return ;
            }

            Gson gson = new Gson();

            response.setContentType("application/json");
            response.setStatus(200);
            response.getWriter().write(gson.toJson(endividualsName));

        } catch (Exception e) {


            response.setStatus(500);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
}
