/*
KATE POHLMAN NIEDERGESES
ADVANCED JAVA
Dr. SHIN
FALL 2020
Electronic Healthcare Management to
track blood pressure, body composition and
provide patient education for singular clinicians

 */
package sample;

        import javafx.application.Application;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXMLLoader;
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.*;
        import javafx.stage.Stage;
        import java.sql.*;
        import java.sql.Connection;

        import java.sql.DriverManager;
        import java.sql.SQLException;
        import java.sql.Statement;


public class KNEHEALTH extends Application {
    //Declare containers and List view UI accessible to entire class
    ObservableList<String> patients = FXCollections.observableArrayList();
    ListView<String> lv = new ListView<>(patients);
    ListView<String> lvReports = new ListView<>(patients);
    ListView<String> lvBPWeight = new ListView<>(patients);

    @Override
    public void start(Stage primaryStage) throws Exception {
        //query the database to get all patients in the system
        getPatients();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Patient Education Stage
        //display images of blood pressure and body mass indexes for patient education
        ImageView bpStageImageView = new ImageView(new Image("https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/other/blood_pressure_charts/basic_blood_pressure_chart.png"));
        ImageView bmiCategoryImageView = new ImageView(new Image("https://files.prokerala.com/health/images/bmi-category.png"));
        HBox hbImages = new HBox();
        hbImages.getChildren().addAll(bpStageImageView, bmiCategoryImageView);

        //Format images
        bpStageImageView.setFitHeight(400);
        bpStageImageView.setFitWidth(650);
        bpStageImageView.setPreserveRatio(true);
        bmiCategoryImageView.setFitHeight(400);
        bmiCategoryImageView.setFitWidth(650);
        bmiCategoryImageView.setPreserveRatio(true);


        //Code to play video
        /*File file = new File("/bloodPressureVideo.mp4");
        String video = file.toURI().toURL().toString();
        final String MEDIA_URL =
                "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";
        Media media = new Media(MEDIA_URL);
        MediaPlayer mp = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mp);
        Button playButton = new Button(">");
        playButton.setOnAction(e-> {
            if(playButton.getText().equals(">")) {
                mp.play();
                playButton.setText("||");
            }else {
                mp.pause();
                playButton.setText(">");
            }
        });

        Button rewindButton = new Button("<<");
        rewindButton.setOnAction(e -> {mp.seek(Duration.ZERO);});
        BorderPane ptEdpane = new BorderPane();
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playButton, rewindButton);
        ptEdpane.setCenter(mediaView);
        ptEdpane.setBottom(hBox);
*/
        //set and display education stage
       FlowPane patientEdPane = new FlowPane();
       patientEdPane.setHgap(10);
       patientEdPane.setVgap(10);
       patientEdPane.setPadding(new Insets(10,10,10,10));
       patientEdPane.getChildren().add(bpStageImageView);
       patientEdPane.getChildren().add(bmiCategoryImageView);

        //patientEdPane.getChildren().add(hbImages);

        Scene patientEdScene = new Scene(patientEdPane, 1200, 450);
        Stage patientEdStage = new Stage();
        patientEdStage.setScene(patientEdScene);
        patientEdStage.setTitle("Patient Education Material");
        patientEdStage.show();


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //CREATE REPORTS STAGE
        //Create Labels and Buttons and Listview
        Label lblShowBP = new Label("Blood Pressure");
        Button btShowRecentBP = new Button("Show Most Recent BP, single patient");
        Button btShowAllBP = new Button("Show All Blood Pressure, single patient");
        Button btShowAllBPStage2 = new Button("Show All Patients, most recent Stage 2");
        Label lblWeightBMI = new Label("Weight / BMI");
        Button btShowRecentWeight = new Button("Show Most Recent Weight, single patient");
        Button btShowAllWeight = new Button("Show All Weight, single patient");
        Button btShowRecentBMI = new Button("Show Most Recent BMI, single patient");
        Button btShowObese = new Button("Show All Patients, most recent Obese");
        Button btShowUnderweight = new Button("Show All Patients, most recent Underweight");
        Label lblPtSSN = new Label("Enter Patient SSN");
        TextField tfSSN1 = new TextField();
        Label lblHistory = new Label("History");
        Button btShowHistory = new Button("Show Patient History");
        TextArea taDisplayReports = new TextArea("DISPLAY RESULTS");

        //set sizes of buttons, labels and textfields
        btShowObese.setPrefSize(250, 50);
        btShowAllBP.setPrefSize(250, 50);
        btShowAllBPStage2.setPrefSize(250, 50);
        btShowAllWeight.setPrefSize(250, 50);
        btShowRecentWeight.setPrefSize(250, 50);
        btShowRecentBMI.setPrefSize(250, 50);
        btShowRecentBP.setPrefSize(250, 50);
        btShowUnderweight.setPrefSize(250, 50);
        lblPtSSN.setPrefSize(250, 50);
        tfSSN1.setPrefSize(250, 50);
        lblHistory.setPrefSize(250, 50);
        btShowHistory.setPrefSize(250, 50);

        //create and set gridpane format
        GridPane gpBtns = new GridPane();
        gpBtns.setHgap(10);
        gpBtns.setVgap(10);
        gpBtns.setPadding(new Insets(10, 10, 10, 10));

        //add buttons to columns
        //Column 0
        gpBtns.add(lblPtSSN, 0, 0);
        gpBtns.add(lblShowBP, 0, 2);
        gpBtns.add(btShowRecentBP, 0, 3);
        gpBtns.add(btShowAllBP, 0, 4);
        gpBtns.add(btShowAllBPStage2, 0, 5);
        gpBtns.add(lblHistory, 0, 9);
        gpBtns.add(btShowHistory, 0, 10);

        //Column 1
        gpBtns.add(tfSSN1, 1, 0);
        gpBtns.add(lblWeightBMI, 1, 2);
        gpBtns.add(btShowRecentWeight, 1, 3);
        gpBtns.add(btShowAllWeight, 1, 4);
        gpBtns.add(btShowRecentBMI, 1, 5);
        gpBtns.add(btShowObese, 1, 6);
        gpBtns.add(btShowUnderweight, 1, 7);

        //place boxes and list into pane, set spacing and padding
        VBox hbReport = new VBox();
        hbReport.getChildren().add(taDisplayReports);
        hbReport.getChildren().add(lvReports);
        hbReport.setPadding(new Insets(10, 10, 10, 10));
        hbReport.setSpacing(10);
        BorderPane reportsPane = new BorderPane();
        reportsPane.setLeft(gpBtns);
        reportsPane.setCenter(hbReport);

        //Set Reports Stage
        Scene reportsScene = new Scene(reportsPane);
        Stage reportsStage = new Stage();
        reportsStage.setTitle("Request Reports");
        reportsStage.setScene(reportsScene);
        reportsStage.show();

        //set selection mode, add listener to Listbox,
        lvReports.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvReports.getSelectionModel().selectedItemProperty().addListener(e -> {
            taDisplayReports.setText("Selected Patient is: " + lvReports.getSelectionModel().getSelectedItem());
            //see which item is selected, reference name to ssn and set in textbox
            tfSSN1.setText(getSSN(lvReports.getSelectionModel().getSelectedItem()));
        });

////////////////////ENTER BLOOD PRESSURE  & WEIGHT STAGE/////////////////////////////////////////////////////////////////
        //Create BLOOD PRESSURE LABELS
        Label lblId = new Label("Patient ID");
        Label lblSys = new Label("Systolic:");
        Label lblDia = new Label("Diastolic:");
        Label lblBpDate = new Label("Enter Date:");

        //CREATE BLOOD PRESSURE TEXTFIELDS & DATE PICKER
        TextField tfBPID = new TextField();
        TextField tfSys = new TextField();
        TextField tfDia = new TextField();
        TilePane tpBp = new TilePane();
        DatePicker dpbpDate = new DatePicker();
        tpBp.getChildren().add(dpbpDate);
        tpBp.setMaxSize(100, 20);
        TextArea displayALL = new TextArea("Display all");

        //Format textfields
        tfBPID.setMaxWidth(100);
        tfSys.setMaxWidth(100);
        tfDia.setMaxWidth(100);

        //CREATE BLOOD PRESSURE BUTTON
        Button btCalcBp = new Button("Calculate Blood Pressure");
        Button btRecBP = new Button("Record Blood Pressure");

        //Weight Label, textfield and buttons, format
        Label lblWeight = new Label("Weight");
        Label lblHeightWt = new Label("Height");
        TextField tfWeight = new TextField();
        TextField tfHeightWt = new TextField();
        tfWeight.setMaxWidth(200);
        tfWeight.setPrefColumnCount(5);
        Button btEnterWeight = new Button("Enter Weight");
        Button btCalculateBMI = new Button("Calculate BMI");
        TextArea tfDisplayBPWeight = new TextArea("Display all read outs here.");
        tfDisplayBPWeight.setPadding(new Insets(10, 10, 10, 10));
        tfDisplayBPWeight.setPrefHeight(300);
        tfDisplayBPWeight.setPrefWidth(500);
        tfDisplayBPWeight.setMaxWidth(1000);
        tfDisplayBPWeight.setPadding(new Insets(10, 10, 10, 10));
        tfDisplayBPWeight.setWrapText(true);

        //VBOXES TO HOLD LABELS AND TEXTFIELDS FOR BP AND WEIGHT
        VBox vblblWeight = new VBox();
        VBox vbTFweight = new VBox();
        VBox vblbl = new VBox();
        VBox vb1tf = new VBox();
        vblbl.getChildren().addAll(lblId, lblSys, lblDia, lblBpDate);
        vb1tf.getChildren().addAll(tfBPID, tfSys, tfDia, tpBp, btCalcBp, btRecBP);
        vblblWeight.getChildren().addAll(lblWeight, lblHeightWt, btEnterWeight, btCalculateBMI);
        vbTFweight.getChildren().addAll(tfWeight, tfHeightWt, lvBPWeight);

        //ListVeiw listener to see which patient selected, cross reference and return ssn and height and populate textfield
        lvBPWeight.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvBPWeight.getSelectionModel().selectedItemProperty().addListener(e -> {
            //get selected Patient Name and display to TextArea
            tfDisplayBPWeight.setText("Selected Patient is: " + lvBPWeight.getSelectionModel().getSelectedItem());
            //get Selected Patient name, query the database to return corresponding SSN, set it to textbox
            tfBPID.setText(getSSN(lvBPWeight.getSelectionModel().getSelectedItem()));
            tfHeightWt.setText(getHeight(lvBPWeight.getSelectionModel().getSelectedItem()));
        });

        //Format VBox
        vblbl.setSpacing(30);
        vb1tf.setSpacing(20);
        vblblWeight.setSpacing(30);
        vbTFweight.setSpacing(20);
        vblbl.setPadding(new Insets(10, 10, 10, 10));
        vb1tf.setPadding(new Insets(10, 10, 10, 10));
        vblblWeight.setPadding(new Insets(10, 10, 10, 10));
        vbTFweight.setPadding(new Insets(10, 10, 10, 10));
        tfDisplayBPWeight.setPadding(new Insets(10, 10, 10, 10));

        //create Hbox, format, add children
        HBox hb1BP = new HBox();
        hb1BP.getChildren().addAll(tfDisplayBPWeight, lv);
        hb1BP.setSpacing(10);
        hb1BP.setPadding(new Insets(10, 10, 10, 10));

        //create flowpane, get children, add
        FlowPane enterBPPane = new FlowPane();
        enterBPPane.getChildren().add(vblbl);
        enterBPPane.getChildren().add(vb1tf);
        enterBPPane.getChildren().add(vblblWeight);
        enterBPPane.getChildren().add(vbTFweight);
        enterBPPane.getChildren().add(hb1BP);

        //Set Blood Pressure/Weight data entry stage
        Scene enterBPScene = new Scene(enterBPPane, 1300, 600);
        Stage stageBP = new Stage();
        stageBP.setTitle("Enter Blood Pressure & Weight");
        stageBP.setScene(enterBPScene);
        stageBP.show();

        ////////////////Enter Patient Data Stage////////////////////////////////////////////////////////////////////////
        //create  pane for labels textfield and buttons
        BorderPane enterDataPane = new BorderPane();
        VBox vBenterPatient1 = new VBox();
        VBox vBenterPatient2 = new VBox();
        HBox hbHoldVbox = new HBox();
        Label lblName = new Label("Enter Name");
        Label lblDOB = new Label("Enter DOB");
        Label lblHeight = new Label("Enter Height");
        Label lblID = new Label("Enter ID");
        TextField tfEnterName = new TextField();
        tfEnterName.setMaxWidth(100);
        TilePane tpDob = new TilePane();
        tpDob.setHgap(10);
        DatePicker dpdobDate = new DatePicker();
        tpDob.getChildren().add(dpdobDate);
        TextField tfEnterHeight = new TextField();
        tfEnterHeight.setMaxWidth(100);
        TextField tfID1 = new TextField();
        tfID1.setMaxWidth(100);
        tfEnterHeight.setMaxWidth(100);
        Label lblPtHstry = new Label("History");

        Button btEnterPtData = new Button("Enter Patient");
        Button btEnterPtHistory = new Button("Enter History");
        TextArea tadisplayData = new TextArea("Enter History and Display Field");
        tadisplayData.setMaxSize(1000, 550);
        tadisplayData.setWrapText(true);
        lblPtHstry.setPrefSize(50, 50);

        //Listview set selection mode and listen and populate ssn textfield
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lv.getSelectionModel().selectedItemProperty().addListener(e -> {
            //get selected Patient Name and display to TextArea
            tadisplayData.setText("Selected Patient is: " + lv.getSelectionModel().getSelectedItem());
            //get Selected Patient name, query the database to return corresponding SSN, set it to textbox
            tfID1.setText(getSSN(lv.getSelectionModel().getSelectedItem()));
        });

        //place labels and buttons in vbox
        vBenterPatient1.getChildren().addAll(lblName, lblID, lblHeight, lblDOB, lblPtHstry, btEnterPtData, btEnterPtHistory);
        vBenterPatient2.getChildren().addAll(tfEnterName, tfID1, tfEnterHeight, tpDob, tadisplayData, lv);
        hbHoldVbox.getChildren().addAll(vBenterPatient1, vBenterPatient2);
        hbHoldVbox.setSpacing(10);
        hbHoldVbox.setPadding(new Insets(10, 10, 10, 10));
        vBenterPatient2.setSpacing(10);
        vBenterPatient1.setSpacing(20);
        enterDataPane.setCenter(hbHoldVbox);

        //set scene and stage
        Scene enterDataScene = new Scene(enterDataPane, 1300, 800);
        Stage stage = new Stage();
        stage.setTitle("Enter Patient");
        stage.setScene(enterDataScene);
        stage.show();

        ////USAGE WARNING PAGE //////////////////////////////////////////////////////////
        //Provide Usage Reminder to User
        FlowPane usageReminderPane = new FlowPane();
        usageReminderPane.setHgap(10);
        usageReminderPane.setVgap(10);
        usageReminderPane.setPadding(new Insets( 10,10,10,10));
        TextArea taUsageReminder = new TextArea("USAGE REMINDER \nENTER WEIGHT AND HEIGHT IN METRIC UNITS \n");
        usageReminderPane.setAlignment(Pos.CENTER);
        usageReminderPane.getChildren().add(taUsageReminder);
        Scene usageReminderScene = new Scene(usageReminderPane, 800, 500);
        Stage usageReminderStage = new Stage();
        usageReminderStage.setScene(usageReminderScene);
        usageReminderStage.setTitle("Usage Reminder");
        usageReminderStage.show();

/////////////////////////////////////////////BUTTONS/////////////////////////////////////////////////////////////////////////////////////
        //Enter Patient Data BUTTONS STAGE
        //enter patient into system
        btEnterPtData.setOnAction(e -> {
            //validate patient data input
            if (tfID1.getText().trim().isEmpty() || tfEnterName.getText().trim().isEmpty() || tfEnterHeight.getText().trim().isEmpty() || (dpdobDate.getValue() == null)) {
                tadisplayData.setText("Missing Data Field \n");
                if (tfEnterName.getText().trim().isEmpty()) {
                    tadisplayData.appendText("Please Enter Name  \n");
                }
                if (tfID1.getText().trim().isEmpty()) {
                    tadisplayData.appendText("Please Enter ID  \n");
                }
                if (tfEnterHeight.getText().trim().isEmpty()) {
                    tadisplayData.appendText("Please Enter Height\n");
                }
                if (dpdobDate.getValue() == null) {
                    tadisplayData.appendText("Please pick a birthdate");
                }
            } else {
                int ssn = Integer.parseInt(tfID1.getText());
                String name = tfEnterName.getText();
                java.sql.Date dob = java.sql.Date.valueOf(dpdobDate.getValue());
                int height = Integer.parseInt(tfEnterHeight.getText());
                //call constructor for Patient and create a patient object
                Patient patient = new Patient(name, ssn, dob, height);
                patient.savePTDB();
                patients.clear();
                getPatients();
                tadisplayData.setText("Patient has been entered into the database.");
            }
        });
        //enter patient history
        btEnterPtHistory.setOnAction(e -> {
            //read in SSN selected and
            if (tfID1.getText().trim().isEmpty()) {
                tadisplayData.setText("Please Select Patient. \n");
            } else {
                int ssn = Integer.parseInt(tfID1.getText());
                String history = tadisplayData.getText();
                saveHistory(ssn, history);
                tadisplayData.setText("History has been entered into the database.");
            }
        });
        /////////////////////////BUTTONS ENTER BLOOD PRESSURE & WEIGHT STAGE//////////////
        //Button to calculate and display Blood Pressure
        btCalcBp.setOnAction(e -> {
            if (tfBPID.getText().trim().isEmpty() || (dpbpDate.getValue() == null || tfSys.getText().trim().isEmpty() || tfDia.getText().trim().isEmpty())) {
                tfDisplayBPWeight.setText("Missing Data Field. \n");
                if (tfBPID.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Select Patient. \n");
                }
                if (tfSys.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Systolic. \n");
                }
                if (tfDia.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Systolic. \n");
                }
                if (dpbpDate.getValue() == null) {
                    tfDisplayBPWeight.appendText("Please pick a Date.\n");
                }
            } else {
                //read in data from textfields
                int ssn = Integer.parseInt(tfBPID.getText());
                int systolic = Integer.parseInt(tfSys.getText());
                int diastolic = Integer.parseInt(tfDia.getText());
                java.sql.Date dateTaken = java.sql.Date.valueOf(dpbpDate.getValue());
                String bpStage;

                //instantiate Blood pressure object
                BloodPressure bp = new BloodPressure(ssn, systolic, diastolic, dateTaken);
                //calculate Blood pressure
                bpStage = bp.calculateStage();
                //display string BP and BP stage
                bp.BpStage = bpStage;
                tfDisplayBPWeight.setText("The patient blood pressure stage is " + bpStage + ".");
                tfDisplayBPWeight.appendText("");
            }
        });
        //button to record blood pressure into database
        btRecBP.setOnAction(e -> {
            if (tfBPID.getText().trim().isEmpty() || (dpbpDate.getValue() == null || tfSys.getText().trim().isEmpty() || tfDia.getText().trim().isEmpty())) {
                tfDisplayBPWeight.setText("Missing Data Field. \n");
                if (tfBPID.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Select Patient. \n");
                }
                if (tfSys.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Systolic. \n");
                }
                if (tfDia.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Systolic. \n");
                }
                if (dpbpDate.getValue() == null) {
                    tfDisplayBPWeight.appendText("Please pick a Date.\n");
                }
            } else {
                //read in data from text fields
                int ssn = Integer.parseInt(tfBPID.getText());
                int systolic = Integer.parseInt(tfSys.getText());
                int diastolic = Integer.parseInt(tfDia.getText());
                java.sql.Date dateTaken = java.sql.Date.valueOf(dpbpDate.getValue());
                String bpStage;
                String firstName;
                //instantiate Blood pressure object
                BloodPressure bp = new BloodPressure(ssn, systolic, diastolic, dateTaken);
                bpStage = bp.calculateStage();
                bp.setStage(bpStage);
                //record to database
                firstName = getName(ssn);
                bp.setFirstName(firstName);
                bp.recordBP();
                tfDisplayBPWeight.setText("Blood Pressure Recorded.");
            }
        });
        //button to enter weight into database
        btEnterWeight.setOnAction(e -> {
            if (tfBPID.getText().trim().isEmpty() || (dpbpDate.getValue() == null || tfWeight.getText().trim().isEmpty())) {
                tfDisplayBPWeight.setText("Missing Data Field. \n");
                if (tfBPID.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Select Patient. \n");
                }
                if (tfWeight.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Weight. \n");
                }
                if (dpbpDate.getValue() == null) {
                    tfDisplayBPWeight.appendText("Please pick a Date.\n");
                }
            } else {
                double weight = Double.parseDouble(tfWeight.getText());
                double height = Double.parseDouble(tfHeightWt.getText());
                int ssn = Integer.parseInt(tfBPID.getText());
                java.sql.Date date = java.sql.Date.valueOf(dpbpDate.getValue());
                String name = getName(ssn);
                BodyComposition bc = new BodyComposition(ssn, name, date, weight, height);
                bc.calculateBMI();
                bc.saveBC();
                tfDisplayBPWeight.setText("Patient Weight has been entered into the database:");
            }
        });
        //button to calculate and display BMI before entering into system
        btCalculateBMI.setOnAction(e -> {
            if (tfBPID.getText().trim().isEmpty() || (dpbpDate.getValue() == null || tfWeight.getText().trim().isEmpty())) {
                tfDisplayBPWeight.setText("Missing Data Field \n");
                if (tfBPID.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Select Patient \n");
                }
                if (tfWeight.getText().trim().isEmpty()) {
                    tfDisplayBPWeight.appendText("Please Enter Weight \n");
                }
                if (dpbpDate.getValue() == null) {
                    tfDisplayBPWeight.appendText("Please pick a Date\n");
                }
            } else {
                //take data from fields, instantiate a BodyComposition object
                double weight = Double.parseDouble(tfWeight.getText());
                double height = Double.parseDouble(tfHeightWt.getText());
                int ssn = Integer.parseInt(tfBPID.getText());
                double bmi;
                String category;
                java.sql.Date date = java.sql.Date.valueOf(dpbpDate.getValue());
                String name = getName(ssn);
                BodyComposition bc = new BodyComposition(ssn, name, date, weight, height);
                bmi = bc.calculateBMI();
                category = bc.calCategory(bmi);
                tfDisplayBPWeight.setText("BMI of the Patient is " + bmi + "%.");
                tfDisplayBPWeight.appendText("   The weight category is " + category + ".");
            }
        });

        /////////////////////////BUTTONS CREATE REPORTS STAGE///////////////////////////
        //button to show most recent blood pressure of a single selected patient
        btShowRecentBP.setOnAction(e -> {
            if (tfSSN1.getText().trim().isEmpty()) {
                tfDisplayBPWeight.setText("Please Select Patient. \n");
            } else {
                taDisplayReports.setText("");
                //read which patient they want from ssn textfield
                int ssn = Integer.parseInt(tfSSN1.getText());
                taDisplayReports.setText("MOST RECENT BLOOD PRESSURE: \nPATIENT SSN: " + ssn);
                taDisplayReports.appendText("\n");

                //query the database and return return the results
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");

                    Statement statement = connection.createStatement();
                    String sqlmessage = "with latestRecords as "
                            + "(SELECT ssn, "
                            + " max(bpDateTaken) as latestDate "
                            + " FROM bloodpressure Group by ssn)"
                            + " SELECT A.ssn, "
                            + " A.latestDate, "
                            + " B.firstName, "
                            + " B.systolic, "
                            + " B.diastolic "
                            + " FROM "
                            + " latestRecords as A "
                            + " LEFT JOIN bloodpressure as B "
                            + " ON A.ssn = B.ssn AND A. latestDate = B.bpDateTaken "
                            + " WHERE A.ssn = " + ssn + ";";

                    ResultSet resultSet = statement.executeQuery(sqlmessage);
                    while (resultSet.next()) {
                        taDisplayReports.appendText("SYSTOLIC: ");
                        taDisplayReports.appendText(resultSet.getString(4));
                        taDisplayReports.appendText("\n");
                        taDisplayReports.appendText("DIASTOLIC: ");
                        taDisplayReports.appendText(resultSet.getString(5));
                        taDisplayReports.appendText("\n");
                        taDisplayReports.appendText("DATE TAKEN: ");
                        taDisplayReports.appendText(resultSet.getString(2));
                        taDisplayReports.appendText("\n");
                        taDisplayReports.appendText("\n");
                    }
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //button to show all the blood pressure recordings of a single selected patient
        btShowAllBP.setOnAction(e -> {
            //read which patient they want from ssn textfield
            int ssn = Integer.parseInt(tfSSN1.getText());
            taDisplayReports.setText("ALL BLOOD PRESSURES FOR PATIENT SSN: " + ssn);
            taDisplayReports.appendText("\n");

            //query the database and return return the results
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = connection.createStatement();
                String insertString = "SELECT bpDateTaken, systolic, diastolic " +
                        "FROM bloodpressure WHERE ssn = "
                        + ssn + ";";
                taDisplayReports.appendText("DATE TAKEN  SYSTOLIC  DIASTOLIC \n");
                ResultSet resultSet = statement.executeQuery(insertString);
                while (resultSet.next()) {
                    taDisplayReports.appendText(resultSet.getString(1) + "\t");
                    taDisplayReports.appendText(resultSet.getString(2) + "\t");
                    taDisplayReports.appendText(resultSet.getString(3) + "\t");
                    taDisplayReports.appendText("\n");
                }
                connection.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //button to show the history of a selected patient
        btShowHistory.setOnAction((e -> {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                System.out.println("Database connected SHOW HISTORY");
                Statement statement = connection.createStatement();
                int ssn = Integer.parseInt(tfSSN1.getText());
                String insertString = "SELECT history FROM patient WHERE SSN = " + ssn + ";";
                ResultSet resultSet = statement.executeQuery(insertString);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                    taDisplayReports.setText(resultSet.getString(1));
                }
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }));
        //button to show the most recent weight of a selected patient
        btShowRecentWeight.setOnAction(e -> {
            if (tfSSN1.getText().trim().isEmpty()) {
                taDisplayReports.setText("Please Select Patient. \n");
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                    Statement statement = connection.createStatement();
                    String ssnPt = tfSSN1.getText();
                    String sqlmessage = " with latestRecord as (SELECT ssn, max(bcDateTaken) as latestDate From bodycomposition Group by ssn) SELECT A.ssn, " +
                            "A.latestDate, B.firstName, B.weightKilo, B.bmi FROM latestRecord as A LEFT JOIN bodycomposition as B " +
                            "ON A.ssn = B.ssn AND A.latestDate = B.bcDateTaken where A.ssn = " + ssnPt;

                    ResultSet resultSet = statement.executeQuery(sqlmessage);
                    // Iterate through the result and print the student names
                    while (true) {
                        if (!resultSet.next()) {
                            break;
                        }
                        taDisplayReports.setText("Patient: " + resultSet.getString(3) + "\n" + "Weight: " + resultSet.getString(4));
                    }
                    connection.close();
                } catch (ClassNotFoundException | SQLException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        //button to show most recent BMI of a single selected patient
        btShowRecentBMI.setOnAction(e -> {
            if (tfSSN1.getText().trim().isEmpty()) {
                taDisplayReports.setText("Please Select Patient. \n");
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = null;
                    connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                    Statement statement = connection.createStatement();
                    Integer ssnPt = Integer.parseInt(tfSSN1.getText());
                    String sqlmessage = "with latestRecords as "
                            + "(SELECT ssn, "
                            + " max(bcDateTaken) as latestDate "
                            + " FROM bodycomposition Group by ssn)"
                            + " SELECT A.ssn, "
                            + " A.latestDate, "
                            + " B.firstName, "
                            + " B.bmi "
                            + " FROM "
                            + " latestRecords as A "
                            + " LEFT JOIN bodycomposition as B "
                            + " ON A.ssn = B.ssn AND A. latestDate = B.bcDateTaken "
                            + " WHERE A.ssn = " + ssnPt + ";";

                    ResultSet resultSet = null;
                    taDisplayReports.setText("Patient: " + ssnPt);
                    resultSet = statement.executeQuery(sqlmessage);

                    while (true) {
                        if (!resultSet.next()) break;
                        taDisplayReports.appendText("\nDate Taken: " + resultSet.getString(2) + "\t" + "BMI: " + resultSet.getString(4));
                    }
                    connection.close();
                } catch (ClassNotFoundException | SQLException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        //button to show all the recorded weights of a single selected patient
        btShowAllWeight.setOnAction(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = null;
                connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = null;
                statement = connection.createStatement();
                int ssnPt = Integer.parseInt(tfSSN1.getText());
                String sqlmessage = "Select bcDateTaken, weightKilo From bodycomposition where ssn = " + ssnPt + ";";
                taDisplayReports.setText("Patient SSN: " + ssnPt);
                taDisplayReports.appendText("\nDate Taken  " + "\t" + "Weight \n");
                ResultSet resultSet = null;
                resultSet = statement.executeQuery(sqlmessage);
                // Iterate through the result and print the student names
                while (true) {
                    if (!resultSet.next()) break;
                    taDisplayReports.appendText(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\n");
                }
                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        //button to show all patients who's most recent weight category places them in the obese range
        btShowObese.setOnAction(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = null;
                connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = null;
                statement = connection.createStatement();
                String sqlmessage = "with latestRecord as (SELECT ssn, max(bcDateTaken) as latestDate"
                        + " From bodycomposition Group by ssn) SELECT A.ssn,"
                        + " A.latestDate,"
                        + " B.firstName,"
                        + " B.weightKilo,"
                        + " B.bmi FROM latestRecord as A"
                        + " LEFT JOIN bodycomposition as B"
                        + " ON A.ssn = B.ssn AND A.latestDate = B.bcDateTaken"
                        + " where bmi > 29.9;";

                taDisplayReports.setText("ALL PATIENTS, MOST RECENT BMI > 29.9 \n\nSSN:    Date Taken:   Name:    Weight:   BMI\n");
                ResultSet resultSet = null;
                resultSet = statement.executeQuery(sqlmessage);


                // Iterate through the result and print the student names
                while (true) {
                    if (!resultSet.next()) break;
                    taDisplayReports.appendText(resultSet.getString(1)
                            + "\t   "
                            + resultSet.getString(2) + "\t  "
                            + resultSet.getString(3) + "\t      "
                            + resultSet.getString(4) + "\t  "
                            + resultSet.getString(5) + "\t\n");
                }

                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        //button to show all patients who's most recent category is in the underweight section
        btShowUnderweight.setOnAction(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = null;
                connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = null;
                statement = connection.createStatement();
                String sqlmessage = "with latestRecord as (SELECT ssn, max(bcDateTaken) as latestDate"
                        + " From bodycomposition Group by ssn) SELECT A.ssn,"
                        + " A.latestDate,"
                        + " B.firstName,"
                        + " B.weightKilo,"
                        + " B.bmi FROM latestRecord as A"
                        + " LEFT JOIN bodycomposition as B"
                        + " ON A.ssn = B.ssn AND A.latestDate = B.bcDateTaken"
                        + " where bmi < 18.5;";

                taDisplayReports.setText("ALL PATIENTS, MOST RECENT BMI < 18.5 \n\nSSN:    Date Taken:   Name:    Weight:   BMI\n");
                ResultSet resultSet = null;
                resultSet = statement.executeQuery(sqlmessage);


                // Iterate through the result and print the student names
                while (true) {
                    if (!resultSet.next()) break;
                    taDisplayReports.appendText(resultSet.getString(1)
                            + "\t   "
                            + resultSet.getString(2) + "\t  "
                            + resultSet.getString(3) + "\t      "
                            + resultSet.getString(4) + "\t  "
                            + resultSet.getString(5) + "\t\n");
                }

                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        //button to show blood pressure of all patient's who's most recent blood pressure is in STAGE 2
        btShowAllBPStage2.setOnAction(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = null;
                connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
                Statement statement = null;
                statement = connection.createStatement();
                String sqlmessage = "WITH latestBPRecord as "
                        + " (SELECT ssn, max(bpDateTaken) as latestBPDate "
                        + " FROM bloodpressure Group by ssn) "
                        + " SELECT  A.ssn, A.latestBPDate, B.firstName, "
                        + " B.systolic,  B.diastolic,  B.bpStage,  B.bpMsg "
                        + " FROM latestBPRecord as A LEFT JOIN bloodpressure as B "
                        + " ON A.ssn = B.ssn AND A.latestBPDate = B.bpDateTaken "
                        + " WHERE bpStage = 'STAGE 2';";

                taDisplayReports.setText("ALL PATIENTS, MOST RECENT BLOOD PRESSURE STAGE 2 \n\nSSN:    Date Taken:   Name:    Systolic:   Diastolic:  Stage:\n");
                ResultSet resultSet = null;
                resultSet = statement.executeQuery(sqlmessage);

                // Iterate through the result and print the student names
                while (true) {
                    if (!resultSet.next()) break;
                    taDisplayReports.appendText(resultSet.getString(1)
                            + "\t   "
                            + resultSet.getString(2) + "\t  "
                            + resultSet.getString(3) + "\t      "
                            + resultSet.getString(4) + "\t  "
                            + resultSet.getString(5) + "\t  "
                            + resultSet.getString(6) + "\t\n");
                }

                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
    }

    //methods to retrieve the ssn from the database using patient name
    private String getSSN(String name) {
        String ssnMap = "";
        //get patients from database and populate the observable arraylist
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = connection.createStatement();
            String sqlmessage = "SELECT ssn FROM patient WHERE firstName = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(sqlmessage);

            // Iterate through the result and print the student names
            while (true) {
                if (!resultSet.next()) break;
                //add name to observable arraylist
                ssnMap = resultSet.getString(1);
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return ssnMap;
    }
    //method to get patient height from database using name
    private String getHeight(String name) {
        String heightMap = "";
        //get patients from database and populate the observable arraylist
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = null;
            statement = connection.createStatement();
            String sqlmessage = "SELECT heightCM FROM patient WHERE firstName = '" + name + "';";
            ResultSet resultSet = null;
            resultSet = statement.executeQuery(sqlmessage);

            while (true) {
                if (!resultSet.next()) break;
                heightMap = resultSet.getString(1);
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return heightMap;
    }
    //method to get a list of all patients who have been entered into the system
    private void getPatients() {

        //get patients from database and populate the observable arraylist
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = null;
            statement = connection.createStatement();
            String sqlmessage = "SELECT firstName, ssn FROM patient;";
            ResultSet resultSet = statement.executeQuery(sqlmessage);

            // Iterate through the result and print the student names
            while (true) {
                if (!resultSet.next()) break;
                //add name to observable arraylist
                patients.add(resultSet.getString(1));
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

    }
    //method to get patient name from the ssn
    private String getName(Integer ssn) {
        String firstName = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = connection.createStatement();
            String insertString = "SELECT firstName FROM patient WHERE SSN = " + ssn + ";";
            ResultSet resultSet = statement.executeQuery(insertString);
            while (resultSet.next()) {
                firstName = resultSet.getString(1);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return firstName;
    }
    //method to save a patient history into the database
    private void saveHistory(int ssn, String history) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ehealth", "root", "root123");
            Statement statement = connection.createStatement();
            String insertString = "update patient set history = \"" + history + "\"where ssn =" + ssn + ";";
            int resultSet = statement.executeUpdate(insertString);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
