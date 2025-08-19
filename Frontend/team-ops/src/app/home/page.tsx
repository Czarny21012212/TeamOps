'use client';
import { useRouter } from "next/navigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Building2, Plus, ArrowRight, Users } from "lucide-react";
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
            <>
                <Navbar activeRoute="/" />
                <div className="min-h-screen bg-black flex items-center justify-center ml-72">
                    <div className="text-white">Ładowanie...</div>
                </div>
            </>
        );
    }

    return(
        <>
            <Navbar activeRoute="/" />
            <div className="min-h-screen bg-black p-6 ml-72">
                <div className="max-w-5xl mx-auto space-y-8">
                    {/* Welcome Header */}
                    <div className="mb-8">
                        <h1 className="text-3xl font-bold text-white mb-2">
                            Witaj, {userData && userData.firstName ? userData.firstName : "Guest"}!
                        </h1>
                        <p className="text-zinc-400">
                            Zarządzaj swoimi zadaniami i śledź postępy
                        </p>
                    </div>

                    {/* Company Creator Button */}
                    {!yourCompanyData.companyId && (
                        <div className="mb-8">
                            <Card className="bg-zinc-900 border-zinc-800">
                                <CardContent className="p-6 text-center">
                                    <Building2 className="w-12 h-12 text-zinc-500 mx-auto mb-4" />
                                    <h3 className="text-lg font-medium text-white mb-2">Nie masz jeszcze firmy</h3>
                                    <p className="text-zinc-400 mb-4">Utwórz swoją pierwszą firmę, aby zacząć zarządzać zespołami</p>
                                    <Button 
                                        onClick={() => setShowCompanyCreator(!showCompanyCreator)}
                                        className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700"
                                    >
                                        <Plus className="w-4 h-4 mr-2" />
                                        {showCompanyCreator ? 'Anuluj' : 'Utwórz firmę'}
                                    </Button>
                                </CardContent>
                            </Card>
                            
                            {showCompanyCreator && (
                                <div className="mt-6">
                                    <CompanyCreator />
                                </div>
                            )}
                        </div>
                    )}

                    {/* Your Company Section */}
                    {yourCompanyData.companyId && (
                        <div className="space-y-4">
                            <h2 className="text-xl font-semibold text-white">Twoja firma</h2>
                            <Card className="bg-zinc-900 border-zinc-800 hover:bg-zinc-800 cursor-pointer transition-colors group"
                                  onClick={() => router.push(`/your-company/${yourCompanyData.companyId}`)}>
                                <CardHeader>
                                    <CardTitle className="text-white flex items-center justify-between">
                                        <div className="flex items-center gap-3">
                                            <Building2 className="w-6 h-6" />
                                            {yourCompanyData.companyName || "Bez nazwy"}
                                        </div>
                                        <ArrowRight className="w-5 h-5 text-zinc-600 group-hover:text-white transition-colors" />
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-zinc-400 mb-4">
                                        {yourCompanyData.companyDescription || "Brak opisu firmy"}
                                    </p>
                                    <div className="flex items-center gap-2 text-zinc-500 text-sm">
                                        <Users className="w-4 h-4" />
                                        Kliknij aby zarządzać działami i zespołami
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    )}

                    {/* Companies You're Part Of */}
                    {companyData.companyId && (
                        <div className="space-y-4">
                            <h2 className="text-xl font-semibold text-white">Firmy, w których pracujesz</h2>
                            <Card className="bg-zinc-900 border-zinc-800 hover:bg-zinc-800 cursor-pointer transition-colors group"
                                  onClick={() => navigateToCompany(companyData.companyId!)}>
                                <CardHeader>
                                    <CardTitle className="text-white flex items-center justify-between">
                                        <div className="flex items-center gap-3">
                                            <Building2 className="w-6 h-6" />
                                            {companyData.companyName || "Bez nazwy"}
                                        </div>
                                        <ArrowRight className="w-5 h-5 text-zinc-600 group-hover:text-white transition-colors" />
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="flex items-center justify-between">
                                        <span className="text-zinc-400">ID Firmy:</span>
                                        <span className="text-zinc-300">{companyData.companyId}</span>
                                    </div>
                                    <div className="flex items-center gap-2 text-zinc-500 text-sm mt-3">
                                        <Users className="w-4 h-4" />
                                        Jesteś członkiem tej firmy
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    )}

                    {/* Empty State */}
                    {!yourCompanyData.companyId && !companyData.companyId && !showCompanyCreator && (
                        <div className="text-center py-12">
                            <Building2 className="w-16 h-16 text-zinc-600 mx-auto mb-4" />
                            <h3 className="text-xl font-medium text-white mb-2">Brak firm</h3>
                            <p className="text-zinc-400 mb-6">Nie jesteś członkiem żadnej firmy i nie masz własnej firmy.</p>
                            <Button 
                                onClick={() => setShowCompanyCreator(true)}
                                className="bg-zinc-800 text-white hover:bg-zinc-700 border border-zinc-700"
                            >
                                <Plus className="w-4 h-4 mr-2" />
                                Utwórz pierwszą firmę
                            </Button>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}