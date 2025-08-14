'use client';
import { useRouter } from "next/navigation";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { CheckSquare, Clock, AlertCircle, User, Building2 } from "lucide-react";
import Navbar from "../components/navbar/page";
import CompanyCreator from "../components/company-creator/page";   

export default function Home({ companyId }: { companyId: number }) {

    type UserData = {
        firstName?: string;
    };

    type CompanyData = {
        companyName?: string;
        companyDescription?: string;
        companyId?: number;
    }

    const router = useRouter();

    const [userData, setUserData] = useState<UserData>({})
    const [companyData, setCompanyData] = useState<CompanyData>({});
    const [yourCompanyData, setYourCompanyData] = useState<CompanyData>({});
    const [isLoading, setIsLoading] = useState(true);

    const [showCompanyCreator, setShowCompanyCreator] = useState(false);

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
        })
        .finally(() => {
            setIsLoading(false);
        });
        
    }, []);

    useEffect(() => {
        if (userData.firstName) {
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
        }
    }, [userData])

     useEffect(() => {
        fetch("http://localhost:8081/api/showYourCompany", {
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
            setYourCompanyData(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });

    }, [])

    function navigateToCompany(companyId: number) {
        if(companyId == null || companyId == undefined) {
            return;
        }
        router.push(`/company/${companyId}`);
    }

    if (isLoading) {
        return (
            <div className="min-h-screen bg-black flex items-center justify-center">
                <div className="text-white">Ładowanie...</div>
            </div>
        );
    }
    return(
        <>
        <Navbar activeRoute="/" />
        <div className="min-h-screen bg-black p-6">
            <div className="max-w-6xl mx-auto space-y-8">
                {/* Welcome Header */}
                <div className="text-center py-8">
                    <h1 className="text-4xl font-bold text-white mb-3">
                        Witaj, {userData && userData.firstName ? userData.firstName : "Guest"}!
                    </h1>
                    <p className="text-zinc-400 text-lg">
                        Zarządzaj swoimi zadaniami i śledź postępy
                    </p>
                    <Button onClick={() => setShowCompanyCreator(!showCompanyCreator)}>Create Company</Button>
                    {
                        showCompanyCreator && (
                            <CompanyCreator />
                        )
                    }
                </div>

                {/* Stats Cards Row */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <Card className="bg-zinc-900 border-zinc-800 hover:bg-zinc-850 transition-colors">
                        <CardContent className="p-6">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-zinc-400 text-sm font-medium">Aktywne zadania</p>
                                    <p className="text-3xl font-bold text-white mt-1">5</p>
                                </div>
                                <Clock className="w-10 h-10 text-blue-500" />
                            </div>
                        </CardContent>
                    </Card>
                    
                    <Card className="bg-zinc-900 border-zinc-800 hover:bg-zinc-850 transition-colors">
                        <CardContent className="p-6">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-zinc-400 text-sm font-medium">Ukończone</p>
                                    <p className="text-3xl font-bold text-white mt-1">12</p>
                                </div>
                                <CheckSquare className="w-10 h-10 text-green-500" />
                            </div>
                        </CardContent>
                    </Card>
                    
                    <Card className="bg-zinc-900 border-zinc-800 hover:bg-zinc-850 transition-colors">
                        <CardContent className="p-6">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-zinc-400 text-sm font-medium">Pilne</p>
                                    <p className="text-3xl font-bold text-white mt-1">2</p>
                                </div>
                                <AlertCircle className="w-10 h-10 text-red-500" />
                            </div>
                        </CardContent>
                    </Card>
                </div>

                {/* Main Content Grid */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    {/* Company Info */}
                    <Card 
                        className={`bg-zinc-900 border-zinc-800 transition-colors ${
                            companyData.companyId ? 'hover:bg-zinc-800 cursor-pointer' : ''
                        }`}
                        onClick={() => companyData.companyId && navigateToCompany(companyData.companyId)}
                    >
                        <CardHeader className="pb-4">
                            <CardTitle className="text-white flex items-center gap-3">
                                <Building2 className="w-6 h-6" />
                                Informacje o firmie
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <div className="space-y-4">
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">Firma:</span>
                                    <span className="text-zinc-200 font-medium">
                                        {companyData ? companyData.companyName || "Brak nazwy" : "Brak firmy"}
                                    </span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">ID:</span>
                                    <span className="text-zinc-200 font-medium">
                                        {companyData ? companyData.companyId || "Brak ID" : "Brak ID"}
                                    </span>
                                </div>
                                
                                {companyData.companyId && (
                                    <div className="pt-4">
                                        <Button 
                                            className="w-full bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700 py-2"
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                navigateToCompany(companyData.companyId!);
                                            }}
                                        >
                                            Panel firmy
                                        </Button>
                                    </div>
                                )}
                            </div>
                        </CardContent>
                    </Card>

                    {/* Profile Card */}
                    <Card className="bg-zinc-900 border-zinc-800">
                        <CardHeader className="pb-4">
                            <CardTitle className="text-white flex items-center gap-3">
                                <User className="w-6 h-6" />
                                Twój profil
                            </CardTitle>
                        </CardHeader>
                        <CardContent>
                            <div className="space-y-4">
                                <div className="flex justify-between items-center">
                                    <span className="text-zinc-400">Imię:</span>
                                    <span className="text-zinc-200 font-medium">
                                        {userData && userData.firstName ? userData.firstName : "Nie określono"}
                                    </span>
                                </div>
                                
                                <div className="pt-4 space-y-3">
                                    <Button className="w-full bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700 py-2">
                                        Edytuj profil
                                    </Button>
                                    <Button variant="outline" className="w-full bg-transparent border-zinc-700 text-zinc-300 hover:bg-zinc-800 hover:text-white py-2">
                                        Ustawienia
                                    </Button>
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                    {yourCompanyData.companyId && (
                        <div>
                            <h1 className="text-white ">Your Company</h1>
                            <Card className="bg-zinc-900 border-zinc-800">
                                <CardHeader className="pb-4">
                                    <CardTitle className="text-white flex items-center gap-3">
                                        <Building2 className="w-6 h-6" />
                                        Twoja firma
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="space-y-4">
                                        <div className="flex justify-between items-center">
                                            <span className="text-zinc-400">Nazwa:</span>
                                            <span className="text-zinc-200 font-medium">
                                                {yourCompanyData ? yourCompanyData.companyName || "Brak nazwy" : "Brak firmy"}
                                            </span>
                                        </div>
                                        <div className="flex justify-between items-center">
                                            <span className="text-zinc-400">Opis:</span>
                                            <span className="text-zinc-200 font-medium">
                                                {yourCompanyData ? yourCompanyData.companyDescription || "Brak opisu" : "Brak opisu"}
                                            </span>
                                        </div>
                                        <Button onClick={() => router.push(`/your-company/${yourCompanyData.companyId}`)}>Przejdz</Button>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    )}
                </div>
            </div>
        </div>
        </>
    );
}