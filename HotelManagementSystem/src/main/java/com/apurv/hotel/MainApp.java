package com.apurv.hotel;

import javafx.application.Application;     //base class for javaFX
import javafx.application.Platform;        //platform.runLater
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;           //buttons,labels,alerts etc
import javafx.scene.image.Image;            //loading image
import javafx.scene.image.ImageView;     //displaying image
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {      //start method -> entry point

        String base = "C:/Users/APURV/Desktop/JAVA/intelliJ/HotelManagementSystem/";   //img location

        // logo and hotel images
        ImageView logo = new ImageView(new Image("file:///" + base + "logo.png"));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);    //avoids distortion of the image

        ImageView hotelImg = new ImageView(new Image("file:///" + base + "hotel.png"));
        hotelImg.setFitHeight(250);
        hotelImg.setPreserveRatio(true);

        // buttons
        Button addRoomBtn = new Button("Add Room");
        Button displayRoomBtn = new Button("Display Rooms");
        Button bookRoomBtn = new Button("Book Room");
        Button displayCustBtn = new Button("Display Customers");
        Button checkoutBtn = new Button("Checkout");
        Button removeRoomBtn = new Button("Remove Room");
        Button exitBtn = new Button("Exit");

        Button[] btns = {addRoomBtn, displayRoomBtn, bookRoomBtn,
                displayCustBtn, checkoutBtn, removeRoomBtn, exitBtn};

        for (Button b : btns) {       //loop to apply consistent styling to all buttons
            b.setPrefWidth(200);
            b.setStyle("-fx-background-color:#5c6bc0; -fx-text-fill:white;");
        }

        // same as backend code of console version
        ArrayList<Room> rooms = new ArrayList<>();
        HotelManagement.loadRooms(rooms);

        ArrayList<Customer> customers = new ArrayList<>();
        HotelManagement.loadCustomers(customers);

        HashMap<Integer, Customer> map = new HashMap<>();
        for (Customer c : customers) {
            map.put(c.getRoomNumber(), c);
        }

        // ui layout
        Label heading = new Label("WELCOME TO APURV'S HOTEL MANAGEMENT SYSTEM");
        heading.setStyle("-fx-text-fill:white; -fx-font-size:18px; -fx-font-weight:bold;");
        //v box left to store buttons
        VBox left = new VBox(15, addRoomBtn, displayRoomBtn, bookRoomBtn,
                displayCustBtn, checkoutBtn, removeRoomBtn, exitBtn);
        left.setAlignment(Pos.CENTER_LEFT);
        //v box right for hotel image
        VBox right = new VBox(hotelImg);
        right.setAlignment(Pos.CENTER);
        //h box center to separate between buttons and image
        HBox center = new HBox(40, left, right);
        center.setAlignment(Pos.CENTER);
        //parent alignment
        VBox root = new VBox(20, logo, heading, center);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color:#1c1f33;");

        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Hotel Management");
        stage.show();

        // add room button
        addRoomBtn.setOnAction(e -> {
            Stage s = new Stage();   //opens a new window when button is clicked

            TextField rno = new TextField();   //text field : scanner sc
            TextField type = new TextField();
            Button submit = new Button("Add");    //user clicks this to submit

            VBox v = new VBox(10,
                    new Label("Room Number:"), rno,
                    new Label("Type (1-Basic,2-Deluxe,3-Suite):"), type,
                    submit);
            v.setAlignment(Pos.CENTER);
            //put the above vbox content into the memory
            s.setScene(new Scene(v, 300, 250));
            s.show();

            submit.setOnAction(ev -> {             //backend logic runs here
                int num = Integer.parseInt(rno.getText());     //get text from the text field and convert to integer
                int t = Integer.parseInt(type.getText());

                RoomType rt = null;         //convert to enum
                if (t == 1) rt = RoomType.STANDARD;
                else if (t == 2) rt = RoomType.DELUXE;
                else if (t == 3) rt = RoomType.SUITE;

                if (rt == null) return;

                Room room;            //create objects
                if (rt == RoomType.DELUXE)
                    room = new DeluxeRoom(num, rt, rt.getPrice(), true, true);
                else if (rt == RoomType.SUITE)
                    room = new SuiteRoom(num, rt, rt.getPrice(), true, true, true, true);
                else
                    room = new Room(num, rt, rt.getPrice());

                rooms.add(room);             //add room,save and show confirmation
                HotelManagement.saveRooms(rooms);

                new Alert(Alert.AlertType.INFORMATION, "Room added").show();
                s.close();
            });
        });

        // display rooms
        displayRoomBtn.setOnAction(e -> {
            VBox v = new VBox(10);

            if (rooms.size() == 0)
                v.getChildren().add(new Label("No rooms available"));

            for (Room r : rooms) {             //enhanced for loop to traverse through each room
                v.getChildren().add(new Label(   //get children returns all the elements inside the vbox
                        r.getRoomNumber() + " | " +
                                r.getRoomType() + " | " +
                                r.getPrice() + " | " +
                                r.isAvailable()));
            }
            //display this in a new window
            new Stage() {{
                setScene(new Scene(v, 400, 300));
                setTitle("Rooms");
                show();
            }};
        });

        // book room
        bookRoomBtn.setOnAction(e -> {

            Stage s = new Stage();
            //take inputs
            TextField cidField = new TextField();
            TextField nameField = new TextField();
            TextField roomField = new TextField();
            TextField daysField = new TextField();
            //submit button
            Button submitBtn = new Button("Book");
            //add 3 images of the rooms
            ImageView basicImg = new ImageView(new Image("file:///" + base + "basic.png"));
            basicImg.setFitWidth(150);
            basicImg.setPreserveRatio(true);

            ImageView deluxeImg = new ImageView(new Image("file:///" + base + "deluxe.png"));
            deluxeImg.setFitWidth(150);
            deluxeImg.setPreserveRatio(true);

            ImageView suiteImg = new ImageView(new Image("file:///" + base + "suite.png"));
            suiteImg.setFitWidth(150);
            suiteImg.setPreserveRatio(true);
            //wrapping the images with labels
            VBox bBox = new VBox(5, basicImg, new Label("Basic"));
            VBox dBox = new VBox(5, deluxeImg, new Label("Deluxe"));
            VBox sBox = new VBox(5, suiteImg, new Label("Suite"));

            bBox.setAlignment(Pos.CENTER);
            dBox.setAlignment(Pos.CENTER);
            sBox.setAlignment(Pos.CENTER);
            //checking the alignment of the images horizontally
            HBox imgRow = new HBox(20, bBox, dBox, sBox);
            imgRow.setAlignment(Pos.CENTER);
            //customer details -> vertically
            VBox form = new VBox(10,
                    new Label("Customer ID:"), cidField,
                    new Label("Name:"), nameField,
                    new Label("Room Number:"), roomField,
                    new Label("Days:"), daysField,
                    submitBtn);
            form.setAlignment(Pos.CENTER);
            //COMBINES EVERYTHING, ROOM TYPE+IMAGE ROW+FORM BELOW
            VBox layout = new VBox(20, new Label("ROOM TYPES"), imgRow, form);
            layout.setAlignment(Pos.CENTER);
            //display the combined stuff
            s.setScene(new Scene(layout, 500, 500));
            s.show();
            //fetch remaining details after submission
            submitBtn.setOnAction(ev -> {
                int id = Integer.parseInt(cidField.getText());
                int roomNo = Integer.parseInt(roomField.getText());
                int days = Integer.parseInt(daysField.getText());

                boolean booked = false;

                for (Room r : rooms) {
                    if (r.getRoomNumber() == roomNo && r.isAvailable()) {

                        r.setAvailable(false); //if room is available, book it and set availability to false
                        //add customer and put the billing logic
                        Customer c = new Customer(id, nameField.getText(), roomNo);
                        customers.add(c);
                        map.put(roomNo, c);

                        HotelManagement.saveRooms(rooms);
                        HotelManagement.saveCustomers(customers);

                        double total = r.getPrice() * days;

                        new Alert(Alert.AlertType.INFORMATION,
                                "Booking Done\nTotal: " + total).show();

                        booked = true;
                        break;
                    }
                }

                if (!booked)
                    new Alert(Alert.AlertType.ERROR, "Room not available").show();

                s.close();
            });
        });

        // display customers
        displayCustBtn.setOnAction(e -> {
            VBox v = new VBox(10);

            if (customers.size() == 0)
                v.getChildren().add(new Label("No customers found"));

            for (Customer c : customers) {
                v.getChildren().add(new Label(
                        c.getCustomerId() + " | " +
                                c.getName() + " | Room: " +
                                c.getRoomNumber()));
            }

            new Stage() {{
                setScene(new Scene(v, 400, 300));
                setTitle("Customers");
                show();
            }};
        });

        // checkout
        checkoutBtn.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setHeaderText("Enter Customer ID");

            d.showAndWait().ifPresent(val -> {    //show, wait for i/p, cont only if user enters something
                int id = Integer.parseInt(val);

                for (int i = 0; i < customers.size(); i++) {    //search customers
                    if (customers.get(i).getCustomerId() == id) {

                        int roomNo = customers.get(i).getRoomNumber();    //fetch room no

                        for (Room r : rooms) {
                            if (r.getRoomNumber() == roomNo) {

                                new Thread(() -> {         //cleaning thread
                                    try { Thread.sleep(3000); } catch (Exception ignored) {}

                                    r.setAvailable(true);    //checkout done

                                    Platform.runLater(() ->
                                            new Alert(Alert.AlertType.INFORMATION,
                                                    "Room cleaned").show());
                                }).start();
                            }
                        }

                        customers.remove(i);
                        map.remove(roomNo);

                        HotelManagement.saveRooms(rooms);
                        HotelManagement.saveCustomers(customers);
                        break;
                    }
                }
            });
        });

        // remove room
        removeRoomBtn.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setHeaderText("Enter Room Number");

            d.showAndWait().ifPresent(val -> {
                int num = Integer.parseInt(val);

                for (int i = 0; i < rooms.size(); i++) {
                    if (rooms.get(i).getRoomNumber() == num &&
                            rooms.get(i).isAvailable()) {

                        rooms.remove(i);
                        HotelManagement.saveRooms(rooms);

                        new Alert(Alert.AlertType.INFORMATION, "Room removed").show();
                        return;
                    }
                }

                new Alert(Alert.AlertType.ERROR,
                        "Room is booked or not found").show();
            });
        });

        exitBtn.setOnAction(e -> stage.close());
    }

    public static void main(String[] args) {
        launch();
    }
}