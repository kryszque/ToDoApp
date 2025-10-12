package kryszque.todoapp.model.tasks;

import java.lang.Object;
import static kryszque.todoapp.model.dates.DatesManager.checkDate;

public class Task {
    private int id;
    private String title;
    private String category;
    private String date;
    private String description;
    private Integer priority;
    private boolean done;

    public Task() {
    }

    //id handling
    public int getId() {
        return this.id;
    }
    //title handling
    public void setTitle(String title) {
        try{
            if(title == null || title.isEmpty()){
                throw new IllegalArgumentException("Title is empty. Provide a valid one.");
            }
            this.title = title;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void editTitle(String new_title) {
        try {
            if (new_title == null || new_title.isEmpty()) {
                throw new IllegalArgumentException("New title is empty. Provide a valid one.");
            }
            if (new_title.equals(this.title)) {
                throw new IllegalArgumentException("New title is the same. Provide a different one.");
            }
            this.title = new_title;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //category handling

    public void setCategory(String category) {
        try{
            if(category == null || category.isEmpty()){
                throw new IllegalArgumentException("Category is empty. Provide a valid one.");
            }
            this.category = category;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCategory() {
        return this.category;
    }

    public void editCategory(String new_category) {
        try {
            if (new_category == null || new_category.isEmpty()) {
                throw new IllegalArgumentException("New category is empty. Provide a valid one.");
            }
            if (new_category.equals(this.category)) {
                throw new IllegalArgumentException("New category is the same. Provide a different one.");
            }
            this.category = new_category;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //date handling

    public void setDate(String date) {
        try{
            if(date == null || date.isEmpty()){
                throw new IllegalArgumentException("Date is empty. Provide a valid one.");
            }
            if(!checkDate(date)){
                throw new IllegalArgumentException("Date is either in invalid format or passed. Provide a valid one.");
            }
            this.date = date;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public String getDate() {
        return this.date;
    }
    //more after implementing dates handling

    //content handling
    public void setDescription(String description) {
        try {
            if (description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Content is empty. Provide a valid one.");
            }
            this.description = description;
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void editContent(String new_content) {
        try{
            if (new_content == null || new_content.isEmpty()) {
                throw new IllegalArgumentException("New content is empty. Provide a valid one.");
            }
            if (new_content.equals(this.description)) {
                throw new IllegalArgumentException("New content is the same. Provide a different one.");
            }
            this.description = new_content;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    //priority handling
    public void setPriority(Integer priority) {
        try{
            if (priority == null || priority < 0 || priority > 10) {
                throw new IllegalArgumentException("Priority must be between 0 and 10.");
            }
            this.priority = priority;
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void editPriority(Integer new_priority) {
        try {
            if (new_priority == null || new_priority < 0 || new_priority > 10) {
                throw new IllegalArgumentException("Priority must be between 0 and 10.");
            }
            if (new_priority.equals(this.priority)) {
                throw new IllegalArgumentException("Priority is the same. Provide a different one.");
            }
            this.priority = new_priority;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    //handling being done
    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Task)){
            return false;
        }
        Task comapred_task = (Task) o;
        return comapred_task.getTitle().equals(this.getTitle()) && comapred_task.getCategory().equals(this.getCategory())
                && comapred_task.getDate().equals(this.getDate()) && comapred_task.getDescription().equals(this.getDescription())
                && comapred_task.getPriority().equals(this.getPriority());
    }

    @Override
    public int hashCode(){
        return this.getTitle().hashCode() + this.getCategory().hashCode() + this.getDate().hashCode() +
                this.getDescription().hashCode() + this.getPriority().hashCode();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + this.getTitle() + "\n");
        sb.append("Category: " + this.getCategory() + "\n");
        sb.append("Date: " + this.getDate() + "\n");
        sb.append("Content: " + this.getDescription() + "\n");
        sb.append("Priority: " + this.getPriority() + "\n");
        return sb.toString();
    }
}