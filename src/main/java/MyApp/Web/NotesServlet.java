package MyApp.Web;

import Server.Constants;
import Server.Http.Request;
import Server.Http.Response;
import Server.Servlet.Servlet;

import java.io.*;
import java.util.ArrayList;

public class NotesServlet implements Servlet {
    public static final String SERVLET_NAME = "notes";
    private final String FILE_NAME = "NotesList.data";
    private ArrayList<String> notes;


    @Override
    public void init() {
        this.notes = deserialNotesList();
    }

    @Override
    public void destroy() {
        serialNotesList(this.notes);
    }

    @Override
    public void service(Request req, Response res) throws IOException {
        String value = req.getParam("note");
        if (value != null)
            notes.add(value);
        StringBuilder notesHTML = new StringBuilder();
        for (int i = 0; i < this.notes.size(); i++) {
            notesHTML
                    .append("\n<tr><td>")
                    .append(i + 1)
                    .append("</td><td>")
                    .append(this.notes.get(i))
                    .append("</td></tr>\n");
        }
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <link rel=\"icon\" href=\"favicon.ico\" type=\"image/x-icon\">" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Notes</title>\n" +
                "    <style>\n" +
                "        table, th, td {\n" +
                "            text-align: center;\n" +
                "            border: 1px solid black;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <form " +
                "       action=\"" + Constants.SERVLET_STRING + SERVLET_NAME + "\"" +
                "       method=\"POST\"" +
                "       autocomplete=\"off\">\n" +
                "           Text: <input type=\"text\" name=\"note\"><br>" +
                "           <input type=\"submit\" value=\"add\">" +
                "           <button type=\"submit\" value=\"add\"> </button>" +
                "   </form>" +
                "   <table style=\"width:100%\">" +
                "       <tr><th>#</th> <th>Text</th></tr>" +
                notesHTML.toString() +
                "   </table>" +
                "</body>" +
                "</html>";
        //loc/servlet/notes?note=text
        res.sendHtml(html);
    }



    private ArrayList<String> deserialNotesList() {
        File list = new File(Constants.WEB_ROOT + "/Data/", FILE_NAME);

        if (!list.exists()) {
            return new ArrayList<>();
        }
        ArrayList<?> al = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(list));
            al = (ArrayList<?>) ois.readObject();
//            if (al instanceof ArrayList<String>) {
//                return al;
//            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (ArrayList<String>) al;
    }

    private void serialNotesList(ArrayList<String> list) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(Constants.WEB_ROOT + "/Data/" + FILE_NAME));
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


