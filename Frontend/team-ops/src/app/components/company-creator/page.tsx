"use client";

import router from "next/router";
import { useState } from "react";

export default function CompanyCreator() {

    const [message, setMessage] = useState<string>("");

    type CompanyData = {
        company: {
            company_name: string;
            company_description: string;
        }
    };

    const [companyData, setCompanyData] = useState<CompanyData>({
        company: {
            company_name: "",
            company_description: ""
        }
    })

    function saveCompanyData(event: React.ChangeEvent<HTMLInputElement>) {
        setCompanyData({
            company: {
                ...companyData.company,
                [event.target.name]: event.target.value
            }
        });
    }

    function handleCheckData(){
        if (companyData.company.company_name === "" || companyData.company.company_description === "") {
            alert("Please fill in all fields.");
            return;
        }
        handleCreateCompany();
    }

    function handleCreateCompany() {
        fetch("http://localhost:8081/api/createCompany", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(companyData),
            credentials: "include"
        })
        .then(resposne => {
            if(resposne.ok){
                return resposne.json();
            }else{
                throw new Error("Network response was not ok");
            }
        })
        .then(data => {
            console.log("Company created successfully:", data);
            setMessage("Company created successfully!");
            router.push(`/company/${data.companyId}`); 
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
            setMessage("Failed to create company. Please try again.");
        })
    }

    

    console.log("Company Data:", companyData);

  return (
    <div className="p-4 m-10 w-50 text-white bg-zinc-800 rounded-lg shadow-lg">
        <h2>Create your own company</h2>
        <label >Company name</label>
        <input name="company_name" placeholder="name" onChange={(event) => saveCompanyData(event)}></input>
        <label>Company description</label>
        <input name="company_description" placeholder="description" onChange={(event) => saveCompanyData(event)}></input>
        <button onClick={() => handleCheckData()}> Create</button>
        {message && <p className="text-red-500">{message}</p>}
    </div>
  );
}