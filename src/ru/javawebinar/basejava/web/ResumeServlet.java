package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');

        response.getWriter().write(getResumes());

//        request.getRequestDispatcher("resumes_table.html").forward(request,response);
    }

    private String getResumes (){
        SqlStorage sqlStorage = Config.get().getStorage();
        List<Resume> resumeList = sqlStorage.getAllSorted();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n" +
                                     "<html lang=\"en\">\n" +
                                     "<head>\n" +
                                     "    <meta charset=\"UTF-8\">\n" +
                                     "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                                     "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                                     "    <link href=\"https://fonts.googleapis.com/css2?family=Istok+Web:ital,wght@0,400;0,700;1,400;1,700&display=swap\"\n" +
                                     "          rel=\"stylesheet\">\n" +
                                     "    <style>\n" +
                                     "        table {\n" +
                                     "            font-family: \"Istok Web\", sans-serif;\n" +
                                     "            border-collapse: collapse;\n" +
                                     "            width: 100%;\n" +
                                     "        }\n" +
                                     "\n" +
                                     "        td, th {\n" +
                                     "            border: 1px solid #dddddd;\n" +
                                     "            text-align: left;\n" +
                                     "            padding: 8px;\n" +
                                     "        }\n" +
                                     "\n" +
                                     "        tr:nth-child(even) {\n" +
                                     "            background-color: #dddddd;\n" +
                                     "        }\n" +
                                     "    </style>\n" +
                                     "    <title>Resume Table</title>\n" +
                                     "</head>\n" +
                                     "<body>\n" +
                                     "<h1>Resume table</h1>\n" +
                                     "<table>\n" +
                                     "    <tr>\n" +
                                     "        <th>UUID</th>\n" +
                                     "        <th>Full name</th>\n" +
                                     "    </tr>");
        resumeList.forEach(r-> {
            stringBuilder.append("<tr>\n" +
                                         "        <td>");
            stringBuilder.append(r.getUuid());
            stringBuilder.append("</td>\n" +
                                         "        <td>");
            stringBuilder.append(r.getFullName());
            stringBuilder.append("</td>\n" +
                                         "    </tr>");
        });
        stringBuilder.append("</table>\n" +
                                     "</body>\n" +
                                     "</html>");
        return stringBuilder.toString();
    }
}
