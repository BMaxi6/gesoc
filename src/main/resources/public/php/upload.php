<?php
if(isset($_POST)){
    $file= $_FILES['file'];

    $fileName= $_FILES['file']['name'];
    $fileTmpName= $_FILES['file']['tmp_name'];
    $fileSize= $_FILES['file']['size'];
    $fileError= $_FILES['file']['error'];
    $fileType= $_FILES['file']['type'];

    $fileExt=explode('.', $fileName);
    $fileActualExt=strtolower(end($fileExt));

    $allowed = array('jpeg', 'jpg', 'png', 'pdf','docx');

    if(in_array($fileActualExt, $allowed)){
        if($fileError==0){
            if(&fileSize<30720){
                //$fileNameNew = uniqid('',true).".".$fileActualExt;
                $fileDestination='uploads/'.$fileNameNew;
                move_uploaded_file($fileTmpName, $fileDestination);
                header("Location: index.php?uploadsuccess");
            }else{
                echo "No puede subir archivos de más de 30MB";
            }
        }else{
            echo "Ha ocurrido un error subiendo el archivo.";  
        }
    }else{
        echo "No puede subir este tipo de archivo.";
    }
}