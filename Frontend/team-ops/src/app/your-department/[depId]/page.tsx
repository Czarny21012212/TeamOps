'use client';
import { Button } from "@/components/ui/button";
import { useParams } from "next/navigation";
import { use, useEffect, useState } from "react";
import UserCreator from '../../components/user-creator/page';

export default function yourDepartmentsPage() {

    const params = useParams();
    const rawDepId = params.depId;
    const depId = Array.isArray(rawDepId) ? parseInt(rawDepId[0], 10) : parseInt(rawDepId as string, 10);

    if (!depId || isNaN(depId)) {
        return <div>Error: Department ID is missing or invalid.</div>;
    }

    type usersData = {
            firstName?: string;
            lastName?: string;
            email?: string;
            finishedTasks?: number;
        };
    
      const [usersData, setUsersData] = useState<usersData[]>([]);
      const [isLoading, setIsLoading] = useState(true);
      const [userCreator, setUserCreator] = useState(false);
    
        useEffect(() => {
            fetch(`http://localhost:8081/api/showUsersFromTeam/${depId}`, {
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
                setUsersData(data)
            })
            .catch(error => {
                console.error("There was a problem with the fetch operation:", error);
            })
            .finally(() => {
                setIsLoading(false);
            });
    
        }, [depId]);
    
        if (isLoading) {
            return (
                <div className="min-h-screen bg-black flex items-center justify-center">
                    <div className="text-white">Ładowanie działu...</div>
                </div>
            );
        }



    return (
        <div>
            <h1 className="m-10">Your Department Page</h1>
            <p className="m-10">Department ID: {depId}</p>
            <div>
                {usersData.map((user, index) => (
                    <div key={index} className="bg-gray-100 p-4 m-10 w-60">
                        <h2>First Name: {user.firstName}</h2>
                        <h2>Last Name: {user.lastName}</h2>
                        <h2>Email: {user.email}</h2>
                        <h2>Finished Tasks: {user.finishedTasks}</h2>
                    </div>
                ))}
            </div>
            <Button onClick={() => setUserCreator(!userCreator)}>Add User</Button>
            {userCreator && <UserCreator depId={depId} />}
        </div>

    )
}