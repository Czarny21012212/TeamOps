'use client';
import { useEffect, useState } from "react";

export default function Home() {

    type UserData = {
        firstName?: string;
        // add other fields if needed
    };

    const [userData, setUserData] = useState<UserData>({})

    useEffect(() => {
        fetch("http://localhost:8081/api/dataAboutUser", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
        setUserData(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
        
    }, []);
    console.log("User Data:", userData);
    return(
        <div>
            <div>
                <h3>Hello {userData ? userData.firstName : "Guest"}</h3>
            </div>
        </div>
    );
}