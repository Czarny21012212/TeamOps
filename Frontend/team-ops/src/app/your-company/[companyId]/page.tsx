"use client";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Building2, Users, Plus, ArrowRight } from "lucide-react";
import DepCreator from '../../components/dep-creator/page';
import Navbar from "../../components/navbar/page";
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
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8081/api/showAllDepartments", {
            credentials: "include"
        })
        .then(res => res.ok ? res.json() : Promise.reject("Failed"))
        .then(data => {
            setAllDepData(data); 
            router.push(`/your-company/${companyId}`)
        })
        .catch(console.error)
        .finally(() => setIsLoading(false));
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

    if (isLoading) {
        return (
            <>
                <Navbar />
                <div className="min-h-screen bg-black flex items-center justify-center ml-72">
                    <div className="text-white">Ładowanie firmy...</div>
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
                        <h1 className="text-3xl font-bold text-white mb-2">Firma</h1>
                        <p className="text-zinc-400">Wszystkie działy w firmie</p>
                    </div>

                    {/* Create Department Button */}
                    <div className="flex justify-end mb-6">
                        <Button 
                            onClick={() => setShowDepartmentCreator(!showDepartmentCreator)}
                            className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700 flex items-center gap-2"
                        >
                            <Plus className="w-4 h-4" />
                            {showDepartmentCreator ? 'Anuluj' : 'Utwórz dział'}
                        </Button>
                    </div>

                    {/* Department Creator */}
                    {showDepartmentCreator && typeof companyId === "number" && !isNaN(companyId) && (
                        <div className="mb-8">
                            <DepCreator companyId={companyId} />
                        </div>
                    )}

                    {/* Departments Grid */}
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        {allDepData.map((dep, index) => (
                            <Card 
                                key={index} 
                                className="bg-zinc-900 border-zinc-800 hover:bg-zinc-800 cursor-pointer transition-colors group"
                                onClick={() => dep.depId && navigateToDepartment(dep.depId)}
                            >
                                <CardHeader className="pb-3">
                                    <CardTitle className="text-white flex items-center justify-between text-lg">
                                        <div className="flex items-center gap-2">
                                            <Building2 className="w-5 h-5 text-zinc-400" />
                                            <span className="truncate">{dep.depName}</span>
                                        </div>
                                        <ArrowRight className="w-4 h-4 text-zinc-600 group-hover:text-white transition-colors" />
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="space-y-2">
                                        <div className="flex items-center justify-between">
                                            <span className="text-zinc-400 text-sm">ID Działu:</span>
                                            <span className="text-zinc-300 text-sm font-medium">{dep.depId}</span>
                                        </div>
                                        <div className="pt-2 border-t border-zinc-800">
                                            <div className="flex items-center gap-1 text-zinc-500 text-xs">
                                                <Users className="w-3 h-3" />
                                                Kliknij aby zobaczyć szczegóły
                                            </div>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>

                    {/* Empty State */}
                    {allDepData.length === 0 && (
                        <div className="text-center py-12">
                            <Building2 className="w-16 h-16 text-zinc-600 mx-auto mb-4" />
                            <h3 className="text-xl font-medium text-white mb-2">Brak działów</h3>
                            <p className="text-zinc-400 mb-4">Ta firma nie ma jeszcze żadnych działów.</p>
                            <Button 
                                onClick={() => setShowDepartmentCreator(true)}
                                className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700"
                            >
                                Utwórz pierwszy dział
                            </Button>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}