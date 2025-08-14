import { Button } from "@/components/ui/button";
import { use, useEffect, useState } from "react";

type ChildProps = {
    companyId: number;
}

 type DepartmentData = {
        companyId: number
        depName: string
    };

export default function DepartmentCreator({companyId}: ChildProps) {

   

    const [depData, setDepData] = useState<DepartmentData>({
         companyId: companyId,
        depName: ""
    });
    const [message, setMessage] = useState<string>("");


    function checkDepData(){
        if (depData.depName === "") {
            setMessage("Please fill in the department name.");
            return;
        }
        handleCreateDepartment();
    }

    function handleCreateDepartment(){
        fetch("http://localhost:8081/api/createDepartment", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(depData),
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("Department created successfully:", data);
            setMessage(data.message || "Department created successfully!");
            window.location.reload();
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
            setMessage("Error creating department: " + error.message);
        });
    }

    console.log("Department Data:", depData);


    return(
        <div>
            <h2>Create department</h2>
            <label>department name</label>
            <input
                placeholder="name"
                name="depName"
                value={depData.depName}
                onChange={(e) =>
                    setDepData({
                        ...depData,
                       [e.target.name]: e.target.value,
                    })
                }
            ></input>
            {message && <p>{message}</p>}
            <Button onClick= {() => checkDepData()}>Create</Button>
        </div>
    );
}