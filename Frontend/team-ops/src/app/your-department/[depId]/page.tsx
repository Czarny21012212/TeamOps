'use client';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { Users, Mail, CheckSquare, Plus } from "lucide-react";
import UserCreator from '../../components/user-creator/page';
import Navbar from "../../components/navbar/page";

export default function YourDepartmentsPage() {

    const params = useParams();
    const rawDepId = params.depId;
    const depId = Array.isArray(rawDepId) ? parseInt(rawDepId[0], 10) : parseInt(rawDepId as string, 10);

    if (!depId || isNaN(depId)) {
        return (
            <>
                <Navbar />
                <div className="min-h-screen bg-black flex items-center justify-center ml-72">
                    <div className="text-red-400">Error: Department ID is missing or invalid.</div>
                </div>
            </>
        );
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
            <>
                <Navbar />
                <div className="min-h-screen bg-black flex items-center justify-center ml-72">
                    <div className="text-white">Ładowanie działu...</div>
                </div>
            </>
        );
    }

    return (
        <>
            <Navbar />
            <div className="min-h-screen bg-black p-6 ml-72">
                <div className="max-w-7xl mx-auto space-y-6">
                    {/* Header */}
                    <div className="mb-8">
                        <h1 className="text-3xl font-bold text-white mb-2">Dział</h1>
                        <p className="text-zinc-400">ID Działu: {depId}</p>
                    </div>

                    {/* Add User Button */}
                    <div className="flex justify-end mb-6">
                        <Button 
                            onClick={() => setUserCreator(!userCreator)}
                            className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700 flex items-center gap-2"
                        >
                            <Plus className="w-4 h-4" />
                            {userCreator ? 'Anuluj' : 'Dodaj użytkownika'}
                        </Button>
                    </div>

                    {/* User Creator */}
                    {userCreator && (
                        <div className="mb-8">
                            <UserCreator depId={depId} />
                        </div>
                    )}

                    {/* Users Grid */}
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        {usersData.map((user, index) => (
                            <Card key={index} className="bg-zinc-900 border-zinc-800">
                                <CardHeader className="pb-3">
                                    <CardTitle className="text-white flex items-center gap-2 text-lg">
                                        <div className="w-8 h-8 bg-zinc-700 rounded-full flex items-center justify-center text-white text-sm font-medium">
                                            {user.firstName?.charAt(0) || 'U'}
                                        </div>
                                        {user.firstName} {user.lastName}
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <div className="flex items-center gap-2 text-zinc-400">
                                        <Mail className="w-4 h-4" />
                                        <span className="text-sm truncate">{user.email}</span>
                                    </div>
                                    <div className="flex items-center gap-2 text-zinc-400">
                                        <CheckSquare className="w-4 h-4" />
                                        <span className="text-sm">Ukończone zadania: {user.finishedTasks || 0}</span>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>

                    {/* Empty State */}
                    {usersData.length === 0 && (
                        <div className="text-center py-12">
                            <Users className="w-16 h-16 text-zinc-600 mx-auto mb-4" />
                            <h3 className="text-xl font-medium text-white mb-2">Brak użytkowników</h3>
                            <p className="text-zinc-400 mb-4">Ten dział nie ma jeszcze żadnych użytkowników.</p>
                            <Button 
                                onClick={() => setUserCreator(true)}
                                className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700"
                            >
                                Dodaj pierwszego użytkownika
                            </Button>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}