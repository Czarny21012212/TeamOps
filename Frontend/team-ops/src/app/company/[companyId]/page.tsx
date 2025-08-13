"use client";

import { error } from "console";
import { useParams, useRouter } from "next/navigation";
import { use, useEffect, useState } from "react";

export default function Company() {

    const params = useParams();
    const companyId = params.companyId;
    const router = useRouter();

    type UserDepData = {
        depName?: string;
        depId?: number;
    }

    const [userDepData, setUserDepData] = useState<UserDepData>({});
    const [allDepData, setAllDepData] = useState<UserDepData[]>([]);

    useEffect(() => {
        fetch("http://localhost:8081/api/showUserDepartment", {
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
            setUserDepData(data)
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
    }, [companyId]);

    useEffect(() => {
        fetch("http://localhost:8081/api/showAllDepartment", {
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
            console.log("User Department Data:", data);
            setAllDepData(data)
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
    }, [companyId])

    function navigateToDepartment(depId: number) {
        if(depId == null || depId == undefined) {
            return;
        }
        router.push(`/department/${depId}`);
    }

    return (
        <div>
            <h1 className="p-4 m-10 w-50">Company Page</h1>

            <p className=" m-10">Twój Team</p>
            <div className="bg-gray-100 p-4 m-10 w-60 hover:bg-gray-200 cursor-pointer">
                <h2>Department Name: {userDepData ? userDepData.depName : "No Department"}</h2>
                <h2>Department ID: {userDepData ? userDepData.depId : "No Department ID"}</h2>
            </div>
            <p className=" m-10">Wszytkie Teamy</p>
            <div>
                {allDepData.map((dep, index) => (
                    <div key={index} className="bg-gray-100 p-4 m-10 w-60 hover:bg-gray-200 cursor-pointer" onClick={() => dep.depId && navigateToDepartment(dep.depId)}>
                        <h2>Department Name: {dep.depName}</h2>
                        <h2>Department ID: {dep.depId}</h2>
                    </div>
                ))}
            </div>
            
        </div>
    )
}

