<?php
    $servername = "localhost";
    $username = "rasp";
    $password = "@mitS3559";
    $dbname = "iot";

    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "UPDATE parking_space SET status = ".$_POST['status']." WHERE space_id = '".$_POST['space_id']."' && lot_id = 1";

    if ($conn->query($sql) === TRUE) {
        echo "Record updated successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }

    $sql1 = "INSERT INTO pending (space_id, lot_id, status) values ('".$_POST['space_id']."', 1, ".$_POST['status'].")";

    if ($conn->query($sql1) === TRUE) {
        echo "Record updated successfully";
    } else {
        echo "Error: " . $sql1 . "<br>" . $conn->error;
    }

    $conn->close();
?> 

