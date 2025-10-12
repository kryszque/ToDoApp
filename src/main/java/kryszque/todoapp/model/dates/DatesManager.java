package kryszque.todoapp.model.dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatesManager {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static boolean checkDate(String date){

        try{
            LocalDate convertedDate = LocalDate.parse(date, formatter);
            return !convertedDate.isBefore(LocalDate.now());
        }catch(DateTimeParseException e){
            return false;
        }
    }

    public static String getCurrentDate(){
        return formatter.format(LocalDate.now());
    }


}
