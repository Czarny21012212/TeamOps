'use client';

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useEffect, useState } from "react";
import { Search, User, UserPlus, Crown } from "lucide-react";

export default function UserCreator({depId}: {depId?: number}) {

    if (!depId) {
        return (
            <div className="min-h-screen bg-black flex items-center justify-center">
                <div className="text-red-400">Error: Department ID is missing.</div>
            </div>
        );
    }

    const [dataVisibleUser, setDataVisibleUser] = useState({
        firstName: "",
        lastName: "",
        email: "",
        userId: 0
    });

    const [addUserData, setAddUserData] = useState({
        dep_id: depId,
        position: "",
        is_leader: false,
        user_id: 0
    });
    const[addUserMessage, setAddUserMessage] = useState("");

    const [initialUserData, setInitialUserData] = useState({
        firstName: "",
        lastName: "",
        userId: 0
    });

    type User = {
        userId: number;
        firstName: string;
        lastName: string;
    };

    const [users, setUsers] = useState<User[]>([]);

    function SearchUser(e: React.ChangeEvent<HTMLInputElement>){
        let array: string[] = e.target.value.split(" ");
        if(array.length > 0){
            setInitialUserData({
                ...initialUserData,
                firstName: array[0] || "",
                lastName: array[1] || ""
            });
        }
        ValidateData();
    }

    function ValidateData(){
        SendInitialUsetData();
    }

    function SendInitialUsetData(){
        fetch("http://localhost:8081/api/searchUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(initialUserData),
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("User data fetched successfully:", data);
            setUsers(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
    }

    function AddUserToDepartment() {
        console.log("Adding user to department with data:", addUserData);
        fetch("http://localhost:8081/api/addUserToDepartment", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(addUserData),
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("User added successfully:", data);
            setAddUserMessage(data.message || "User added successfully!");
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
            setAddUserMessage("Something went wrong");
        });
    }

    useEffect(() => {
        if (addUserData.user_id !== 0) {
            fetch(`http://localhost:8081/api/dataOfGivenUser/${addUserData.user_id}`, {
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
                console.log("User data fetched successfully:", data);
                setDataVisibleUser(data)
            })
            .catch(error => {
                console.error("There was a problem with the fetch operation:", error);
            });
        }
    }, [addUserData.user_id]);

    return (
        <div className="bg-zinc-950 border border-zinc-800 rounded-lg p-6 mb-6">
            <div className="mb-6">
                <h2 className="text-xl font-bold text-white mb-2">Dodaj użytkownika</h2>
                <p className="text-zinc-400">Dodaj nowego użytkownika do departamentu ID: {depId}</p>
            </div>

                    <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                        {/* Main Form */}
                        <Card className="bg-zinc-900 border-zinc-800">
                            <CardHeader>
                                <CardTitle className="text-white flex items-center gap-2">
                                    <UserPlus className="w-5 h-5" />
                                    Formularz użytkownika
                                </CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-6">
                                {/* Leader Toggle */}
                                <div className="space-y-2">
                                    <label className="text-zinc-300 text-sm font-medium">Rola w zespole</label>
                                    <div 
                                        onClick={() => setAddUserData({...addUserData, is_leader: !addUserData.is_leader})}
                                        className={`flex items-center justify-between p-4 rounded-lg border-2 cursor-pointer transition-all duration-200 ${
                                            addUserData.is_leader 
                                                ? 'bg-yellow-500/10 border-yellow-500 hover:bg-yellow-500/20' 
                                                : 'bg-zinc-800 border-zinc-700 hover:border-zinc-600'
                                        }`}
                                    >
                                        <div className="flex items-center gap-3">
                                            <div className={`w-10 h-10 rounded-full flex items-center justify-center transition-colors ${
                                                addUserData.is_leader 
                                                    ? 'bg-yellow-500 text-black' 
                                                    : 'bg-zinc-700 text-zinc-400'
                                            }`}>
                                                <Crown className="w-5 h-5" />
                                            </div>
                                            <div>
                                                <p className={`font-medium ${
                                                    addUserData.is_leader ? 'text-yellow-400' : 'text-white'
                                                }`}>
                                                    {addUserData.is_leader ? 'Lider zespołu' : 'Członek zespołu'}
                                                </p>
                                                <p className="text-zinc-400 text-sm">
                                                    {addUserData.is_leader 
                                                        ? 'Użytkownik będzie miał uprawnienia lidera' 
                                                        : 'Standardowe uprawnienia użytkownika'
                                                    }
                                                </p>
                                            </div>
                                        </div>
                                        <div className={`w-5 h-5 rounded border-2 flex items-center justify-center ${
                                            addUserData.is_leader 
                                                ? 'bg-yellow-500 border-yellow-500' 
                                                : 'border-zinc-600'
                                        }`}>
                                            {addUserData.is_leader && (
                                                <svg className="w-3 h-3 text-black" fill="currentColor" viewBox="0 0 20 20">
                                                    <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                                                </svg>
                                            )}
                                        </div>
                                    </div>
                                </div>

                                {/* Position Input */}
                                <div className="space-y-2">
                                    <label className="text-zinc-300 text-sm font-medium">Stanowisko</label>
                                    <input 
                                        type="text"
                                        placeholder="Wprowadź stanowisko..."
                                        value={addUserData.position}
                                        onChange={(e) => setAddUserData({...addUserData, position: e.target.value})} 
                                        className="w-full bg-zinc-800 border border-zinc-700 text-white rounded-lg px-4 py-3 focus:outline-none focus:border-zinc-600"
                                    />
                                </div>

                                {/* Search Input */}
                                <div className="space-y-2">
                                    <label className="text-zinc-300 text-sm font-medium flex items-center gap-2">
                                        <Search className="w-4 h-4" />
                                        Wyszukaj użytkownika
                                    </label>
                                    <input 
                                        type="text"
                                        placeholder="Wpisz imię i nazwisko..."
                                        onChange={(e) => SearchUser(e)}
                                        className="w-full bg-zinc-800 border border-zinc-700 text-white rounded-lg px-4 py-3 focus:outline-none focus:border-zinc-600"
                                    />
                                </div>

                                {/* Search Results */}
                                {users.length > 0 && (
                                    <div className="space-y-2">
                                        <label className="text-zinc-300 text-sm font-medium">Wyniki wyszukiwania</label>
                                        <div className="bg-zinc-800 border border-zinc-700 rounded-lg p-3 max-h-40 overflow-y-auto">
                                            {users.map((user, index) => (
                                                <div 
                                                    key={index}
                                                    onClick={() => setAddUserData({...addUserData, user_id: user.userId})}
                                                    className="flex items-center gap-3 p-2 rounded hover:bg-zinc-700 cursor-pointer transition-colors"
                                                >
                                                    <div className="w-8 h-8 bg-zinc-700 rounded-full flex items-center justify-center text-white text-sm font-medium">
                                                        {user.firstName.charAt(0)}
                                                    </div>
                                                    <span className="text-white">{user.firstName} {user.lastName}</span>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                )}

                                {/* Add Button */}
                                <Button 
                                    onClick={() => AddUserToDepartment()}
                                    disabled={addUserData.user_id === 0 || !addUserData.position}
                                    className="w-full bg-zinc-800 hover:bg-zinc-700 text-white py-3 disabled:opacity-50 disabled:cursor-not-allowed"
                                >
                                    Dodaj użytkownika
                                </Button>

                                {/* Success Message */}
                                {addUserMessage && (
                                    <div className={`p-3 rounded-lg ${
                                        addUserMessage.includes("successfully") || addUserMessage.includes("pomyślnie")
                                            ? "bg-green-900/50 border border-green-700 text-green-300"
                                            : "bg-red-900/50 border border-red-700 text-red-300"
                                    }`}>
                                        {addUserMessage}
                                    </div>
                                )}
                            </CardContent>
                        </Card>

                        {/* Selected User Preview */}
                        {addUserData.user_id !== 0 && (
                            <Card className="bg-zinc-900 border-zinc-800">
                                <CardHeader>
                                    <CardTitle className="text-white flex items-center gap-2">
                                        <User className="w-5 h-5" />
                                        Wybrany użytkownik
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="space-y-4">
                                        <div className="flex items-center gap-4">
                                            <div className="w-16 h-16 bg-zinc-700 rounded-full flex items-center justify-center text-white text-xl font-bold">
                                                {dataVisibleUser.firstName.charAt(0)}
                                            </div>
                                            <div>
                                                <h3 className="text-lg font-semibold text-white">
                                                    {dataVisibleUser.firstName} {dataVisibleUser.lastName}
                                                </h3>
                                                <p className="text-zinc-400">{dataVisibleUser.email}</p>
                                            </div>
                                        </div>
                                        
                                        <div className="space-y-3 pt-4 border-t border-zinc-800">
                                            <div className="flex justify-between">
                                                <span className="text-zinc-400">Stanowisko:</span>
                                                <span className="text-white font-medium">{addUserData.position || "Nie określono"}</span>
                                            </div>
                                            <div className="flex justify-between">
                                                <span className="text-zinc-400">Rola:</span>
                                                <span className={`font-medium flex items-center gap-1 ${
                                                    addUserData.is_leader ? "text-yellow-400" : "text-white"
                                                }`}>
                                                    {addUserData.is_leader && <Crown className="w-4 h-4" />}
                                                    {addUserData.is_leader ? "Lider" : "Członek zespołu"}
                                                </span>
                                            </div>
                                            <div className="flex justify-between">
                                                <span className="text-zinc-400">ID Departamentu:</span>
                                                <span className="text-white font-medium">{depId}</span>
                                            </div>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        )}
                    </div>
                </div>
    );
}