'use client';
import { use, useEffect, useState } from "react";

export default function Home() {

    type UserData = {
        firstName?: string;
        
    };

    type CompanyData = {
        companyName?: string;
        companyId?: number;
    }

    const [userData, setUserData] = useState<UserData>({})
    const [companyData, setCompanyData] = useState<CompanyData>({});

    useEffect(() => {
        fetch("http://localhost:8081/api/dataAboutUser", {
            method: "GET",
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

    useEffect(() => {
        fetch("http://localhost:8081/api/showCompany", {
            method: "GET",
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            setCompanyData(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });

    }, [userData])
    return(
        <div>
            <div>
                <h3 className="p-4 m-10 w-50">Hello {userData && userData.firstName ? userData.firstName : "Guest"}</h3>
                <div className="bg-gray-100 p-4 m-10 w-50 hover:bg-gray-200 cursor-pointer">
                    <h2>Company Name: {companyData ? companyData.companyName : "No Company"}</h2>
                    <h2>Company ID: {companyData ? companyData.companyId : "No Company ID"}</h2>
                </div>
            </div>
        </div>
    );
}