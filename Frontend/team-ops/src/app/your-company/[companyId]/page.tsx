"use client";

import { Button } from "@/components/ui/button";
import { error } from "console";
import { useParams, useRouter } from "next/navigation";
import { use, useEffect, useState } from "react";
import DepCreator from '../../components/dep-creator/page';
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

type UserDepData = {
        depName?: string;
        depId?: number;
    }

export default function YourCompany() {

    const params = useParams();
    const companyId = params.companyId ? Number(params.companyId) : undefined;
    const router = useRouter();


    const [userDepData, setUserDepData] = useState<UserDepData>({});
    const [allDepData, setAllDepData] = useState<UserDepData[]>([]);
    const [showDepartmentCreator, setShowDepartmentCreator] = useState(false);

    useEffect(() => {
         fetch("http://localhost:8081/api/showAllDepartments", {
                credentials: "include"
            })
            .then(res => res.ok ? res.json() : Promise.reject("Failed"))
            .then(data => {setAllDepData(data); router.push(`/your-company/${companyId}`)})
            .catch(console.error);
    }, []);

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

    function navigateToDepartment(depId: number) {
        if(depId == null || depId == undefined) {
            return;
        }
        router.push(`/your-department/${depId}`);
    }

    return (
        <div>
            <h1 className="m-10">Company Page</h1>
            <p className="m-10">Wszytkie Teamy</p>
            <div className="grid grid-cols-5 ">
                {allDepData.map((dep, index) => (
                    <div key={index} className="bg-gray-100 p-4 m-10 w-60 hover:bg-gray-200 cursor-pointer" onClick={() => dep.depId && navigateToDepartment(dep.depId)}>
                        <h2>Department Name: {dep.depName}</h2>
                        <h2>Department ID: {dep.depId}</h2>
                    </div>
                ))}
            </div>
            <Button className="m-10" onClick={() => setShowDepartmentCreator(!showDepartmentCreator)}>Create Department</Button>
            {showDepartmentCreator && typeof companyId === "number" && !isNaN(companyId) && <DepCreator companyId={companyId} />}
        </div>
    )
}

