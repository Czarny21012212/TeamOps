'use client';

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { CheckSquare, Clock, AlertCircle, User, Building2 } from "lucide-react";

export default function Home({ companyId }: { companyId: number }) {
    type UserData = { firstName?: string };
    type CompanyData = { companyName?: string; companyId?: number };

    const router = useRouter();

    const [userData, setUserData] = useState<UserData>({});
    const [companyData, setCompanyData] = useState<CompanyData>({});
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8081/api/dataAboutUser", { method: "GET", credentials: "include" })
            .then(res => res.ok ? res.json() : Promise.reject("Network response was not ok"))
            .then(data => setUserData(data))
            .catch(err => console.error("Fetch error:", err))
            .finally(() => setIsLoading(false));
    }, []);

    useEffect(() => {
        if (userData.firstName) {
            fetch("http://localhost:8081/api/showCompany", { method: "GET", credentials: "include" })
                .then(res => res.ok ? res.json() : Promise.reject("Network response was not ok"))
                .then(data => setCompanyData(data))
                .catch(err => console.error("Fetch error:", err));
        }
    }, [userData]);

    function navigateToCompany(companyId: number) {
        if (!companyId) return;
        router.push(`/company/${companyId}`);
    }

    if (isLoading) {
        return (
            <div className="min-h-screen bg-black flex items-center justify-center">
                <div className="text-white">Ładowanie...</div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-black p-4">
            <div className="max-w-6xl mx-auto space-y-6">
                {/* Welcome Header */}
                <div className="text-center py-8">
                    <h1 className="text-3xl font-bold text-white mb-2">
                        Witaj, {userData.firstName || "Guest"}!
                    </h1>
                    <p className="text-zinc-400">Zarządzaj swoimi zadaniami i śledź postępy</p>
                </div>

                {/* Stats Cards */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
                    <Card className="bg-zinc-900 border-zinc-800">
                        <CardContent className="p-6 flex justify-between items-center">
                            <div>
                                <p className="text-zinc-400 text-sm">Aktywne zadania</p>
                                <p className="text-2xl font-bold text-white">5</p>
                            </div>
                            <Clock className="w-8 h-8 text-blue-500" />
                        </CardContent>
                    </Card>

                    <Card className="bg-zinc-900 border-zinc-800">
                        <CardContent className="p-6 flex justify-between items-center">
                            <div>
                                <p className="text-zinc-400 text-sm">Ukończone</p>
                                <p className="text-2xl font-bold text-white">12</p>
                            </div>
                            <CheckSquare className="w-8 h-8 text-green-500" />
                        </CardContent>
                    </Card>

                    <Card className="bg-zinc-900 border-zinc-800">
                        <CardContent className="p-6 flex justify-between items-center">
                            <div>
                                <p className="text-zinc-400 text-sm">Pilne</p>
                                <p className="text-2xl font-bold text-white">2</p>
                            </div>
                            <AlertCircle className="w-8 h-8 text-red-500" />
                        </CardContent>
                    </Card>
                </div>

                {/* Company & Profile */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div className="space-y-4">
                        {/* Company Info */}
                        <Card
                            className={`bg-zinc-900 border-zinc-800 transition-colors ${
                                companyData.companyId ? 'hover:bg-zinc-800 cursor-pointer' : ''
                            }`}
                            onClick={() => companyData.companyId && navigateToCompany(companyData.companyId)}
                        >
                            <CardHeader>
                                <CardTitle className="text-white flex items-center gap-2">
                                    <Building2 className="w-5 h-5" />
                                    Informacje o firmie
                                </CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-2">
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">Firma:</span>
                                    <span className="text-zinc-300 font-medium">{companyData.companyName || "Brak nazwy"}</span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">ID:</span>
                                    <span className="text-zinc-300 font-medium">{companyData.companyId || "Brak ID"}</span>
                                </div>
                                {companyData.companyId && (
                                    <Button
                                        className="w-full bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700 pt-3"
                                        onClick={(e) => { e.stopPropagation(); navigateToCompany(companyData.companyId!); }}
                                    >
                                        Panel firmy
                                    </Button>
                                )}
                            </CardContent>
                        </Card>

                        {/* Profile Card */}
                        <Card className="bg-zinc-900 border-zinc-800">
                            <CardHeader>
                                <CardTitle className="text-white flex items-center gap-2">
                                    <User className="w-5 h-5" />
                                    Twój profil
                                </CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-3">
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">Imię:</span>
                                    <span className="text-zinc-300 font-medium">{userData.firstName || "Nie określono"}</span>
                                </div>
                                <div className="pt-3 space-y-2">
                                    <Button className="w-full bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700">
                                        Edytuj profil
                                    </Button>
                                    <Button variant="outline" className="w-full bg-transparent border-zinc-700 text-zinc-300 hover:bg-zinc-800 hover:text-white">
                                        Ustawienia
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>
        </div>
    );
}
