'use client';

import { data } from "autoprefixer";
import { error } from "console";
import { useState } from "react";

export default function UserCreator({depId}: {depId?: number}) {

    if (!depId) {
        return <div>Error: Department ID is missing.</div>;
    }

    const [initialUserData, setInitialUserData] = useState({
        firstName: "",
        lastName: "",
    });

    type User = {
        firstName: string;
        lastName: string;
        // add other properties if needed
    };

    const [users, setUsers] = useState<User[]>([]);

    function SearchUser(e: React.ChangeEvent<HTMLInputElement>){
       
        let array: string[] = e.target.value.split(" ");
        if(array.length > 0){
            setInitialUserData({
                ...initialUserData,
                firstName: array[0] || "",
                lastName: array[1] || ""
            });
        }
        ValidateData();
    }

    function ValidateData(){
        SendInitialUsetData();
    }

    function SendInitialUsetData(){
        fetch("http://localhost:8081/api/searchUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(initialUserData),
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("User data fetched successfully:", data);
            setUsers(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
    }


    return (
        <div>
            <h1 className="m-10">User Creator Page</h1>
            <p className="m-10">Create a new user for Department ID: {depId}</p>
            <label>Search user</label>
            <input onChange={(e) => SearchUser(e)}></input>
            <div>
                {users.map((user, index) => (
                    <div key={index} >
                        <p>{user.firstName} {user.lastName}</p>
                    </div>
                ))})
            </div>        
        </div>
    )

}