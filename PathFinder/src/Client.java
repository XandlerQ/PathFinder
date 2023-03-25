/*
height matrix JSON format
{
    "min": 0,
    "max": 1000,
    "size": 3,
    "data":
    [
        [a11, a12, a13],
        [a21, a22, a23],
        [a31, a32, a33]
    ]
}

solution JSON format
{
    "energy": 486,
    "size": 20,
    "path":
    [
        [1,1],
        [1,2],
        [2,3],
        ...
    ]
}
 */

/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;
 */

import com.google.gson.Gson;

public class Client {

    String matrixToJsonString(Matrix matrix, double minElevation, double maxElevation) {
        int n = matrix.getN();
        String json = "{\n";
        json.concat("\"min\": " + minElevation + ",\n")
                .concat("\"max\": " + maxElevation + ",\n")
                .concat("\"size\": " + n + ",\n")
                .concat("\"data\": " + n + ",\n[\n");

        for (int i = 0; i < n; i++) {
            json.concat("[");
            for (int j = 0; j < n; j++) {
                json.concat(String.valueOf(matrix.getVal(i,j)));
                if(j != n - 1) json.concat(", ");
            }
            if(i != n - 1) json.concat("],\n");
            else json.concat("]\n");
        }

        json.concat("]\n}");

        return json;
    }


    
}
