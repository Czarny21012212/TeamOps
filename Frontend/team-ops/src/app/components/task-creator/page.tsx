'use client';

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Badge } from "@/components/ui/badge";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { User, ChevronDown, Check, AlertCircle, Send, X } from "lucide-react";

type users = {
    userId: number;
    firstName: string;
    lastName: string;
}

type Task = {
    title: string;
    content: string;
    difficult_levels: number;
    user_id: number;
}

export default function TaskCreator({ depId }: { depId: number }) {
    const [displayUsers, setDisplayUsers] = useState(false);
    const [users, setUsers] = useState<users[]>([]);
    const [chosenUser, setChosenUser] = useState<users>();
    const [message, setMessage] = useState<string>("");
    const [isLoading, setIsLoading] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [task, setTask] = useState<Task>({
        title: "",
        content: "",
        difficult_levels: 0,
        user_id: 0
    });

    useEffect(() => {
        setIsLoading(true);
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
            setUsers(data);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        })
        .finally(() => {
            setIsLoading(false);
        });
    }, [depId]);

    function SearchUser(userId: number) {
        fetch(`http://localhost:8081/api/dataOfGivenUser/${userId}`, {
            method: "GET",
            credentials: 'include'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            setChosenUser(data);
            setTask({...task, user_id: userId});
            setDisplayUsers(false);
        })
        .catch(error => {
            console.log("error: " + error);
        });
    }

    function SendTask() {
        if (!task.title || !task.content || !chosenUser) {
            setMessage("Wypełnij wszystkie pola i wybierz użytkownika");
            return;
        }

        setIsSubmitting(true);
        setMessage("");

        fetch('http://localhost:8081/api/createTask', {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(task),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            setMessage(data.message || "Zadanie utworzone pomyślnie!");
            // Reset form
            setTask({
                title: "",
                content: "",
                difficult_levels: 0,
                user_id: 0
            });
            setChosenUser(undefined);
            console.log(data);
        })
        .catch(error => {
            setMessage("Błąd podczas tworzenia zadania");
            console.log("error: " + error);
        })
        .finally(() => {
            setIsSubmitting(false);
        });
    }

    const getDifficultyColor = (level: number) => {
        if (level <= 2) return "bg-green-900/30 text-green-400 border-green-800";
        if (level <= 4) return "bg-yellow-900/30 text-yellow-400 border-yellow-800";
        return "bg-red-900/30 text-red-400 border-red-800";
    };

    const getDifficultyText = (level: number) => {
        if (level <= 2) return "Łatwy";
        if (level <= 4) return "Średni";
        return "Trudny";
    };

    return (
        <Card className="bg-zinc-900 border-zinc-800 w-full">
            <CardHeader>
                <CardTitle className="text-white flex items-center gap-2">
                    <Send className="w-5 h-5" />
                    Utwórz nowe zadanie
                </CardTitle>
                <CardDescription className="text-zinc-400">
                    Przydziel zadanie pracownikowi z działu
                </CardDescription>
            </CardHeader>
            
            <CardContent className="space-y-6">
                {/* Task Title */}
                <div className="space-y-2">
                    <Label htmlFor="title" className="text-zinc-200">
                        Nazwa zadania
                    </Label>
                    <Input
                        id="title"
                        name="title"
                        value={task.title}
                        onChange={(e) => setTask({...task, [e.target.name]: e.target.value})}
                        placeholder="Wprowadź nazwę zadania..."
                        className="bg-zinc-800 border-zinc-700 text-white placeholder:text-zinc-500"
                    />
                </div>

                {/* User Selection */}
                <div className="space-y-2">
                    <Label className="text-zinc-200">
                        Wybierz pracownika
                    </Label>
                    <div className="relative">
                        <Button
                            variant="outline"
                            onClick={() => setDisplayUsers(!displayUsers)}
                            className="w-full justify-between bg-zinc-800 border-zinc-700 text-white hover:bg-zinc-700"
                            disabled={isLoading}
                        >
                            {chosenUser ? (
                                <div className="flex items-center gap-2">
                                    <User className="w-4 h-4" />
                                    {chosenUser.firstName} {chosenUser.lastName}
                                </div>
                            ) : (
                                <span className="text-zinc-400">
                                    {isLoading ? "Ładowanie..." : "Wybierz pracownika..."}
                                </span>
                            )}
                            <ChevronDown className={`w-4 h-4 transition-transform ${displayUsers ? 'rotate-180' : ''}`} />
                        </Button>
                        
                        {displayUsers && (
                            <Card className="absolute top-full left-0 right-0 z-10 mt-1 bg-zinc-800 border-zinc-700">
                                <CardContent className="p-2">
                                    {users.length === 0 ? (
                                        <div className="text-zinc-400 text-center py-2">
                                            Brak dostępnych pracowników
                                        </div>
                                    ) : (
                                        <div className="space-y-1">
                                            {users.map((user, index) => (
                                                <Button
                                                    key={index}
                                                    variant="ghost"
                                                    onClick={() => SearchUser(user.userId)}
                                                    className="w-full justify-start text-white hover:bg-zinc-700"
                                                >
                                                    <User className="w-4 h-4 mr-2" />
                                                    {user.firstName} {user.lastName}
                                                    {chosenUser?.userId === user.userId && (
                                                        <Check className="w-4 h-4 ml-auto text-green-500" />
                                                    )}
                                                </Button>
                                            ))}
                                        </div>
                                    )}
                                </CardContent>
                            </Card>
                        )}
                    </div>
                </div>

                {/* Task Content */}
                <div className="space-y-2">
                    <Label htmlFor="content" className="text-zinc-200">
                        Opis zadania
                    </Label>
                    <Textarea
                        id="content"
                        name="content"
                        value={task.content}
                        onChange={(e) => setTask({...task, [e.target.name]: e.target.value})}
                        placeholder="Opisz szczegółowo zadanie do wykonania..."
                        className="bg-zinc-800 border-zinc-700 text-white placeholder:text-zinc-500 min-h-[100px]"
                    />
                </div>

                {/* Difficulty Level */}
                <div className="space-y-2">
                    <Label htmlFor="difficulty" className="text-zinc-200">
                        Poziom trudności
                    </Label>
                    <div className="flex items-center gap-4">
                        <Input
                            id="difficulty"
                            name="difficult_levels"
                            type="number"
                            min="1"
                            max="5"
                            value={task.difficult_levels || ""}
                            onChange={(e) => setTask({...task, [e.target.name]: parseInt(e.target.value) || 0})}
                            placeholder="1-5"
                            className="bg-zinc-800 border-zinc-700 text-white placeholder:text-zinc-500 w-24"
                        />
                        {task.difficult_levels > 0 && (
                            <Badge 
                                variant="outline" 
                                className={getDifficultyColor(task.difficult_levels)}
                            >
                                {getDifficultyText(task.difficult_levels)} ({task.difficult_levels}/5)
                            </Badge>
                        )}
                    </div>
                    <p className="text-xs text-zinc-500">
                        Skala 1-5: 1-2 (Łatwy), 3-4 (Średni), 5 (Trudny)
                    </p>
                </div>

                {/* Action Buttons */}
                <div className="flex gap-3 pt-4">
                    <Button
                        onClick={SendTask}
                        disabled={isSubmitting || !task.title || !task.content || !chosenUser}
                        className="flex-1 bg-white text-black hover:bg-zinc-200 disabled:opacity-50"
                    >
                        {isSubmitting ? (
                            <>Tworzenie...</>
                        ) : (
                            <>
                                <Send className="w-4 h-4 mr-2" />
                                Utwórz zadanie
                            </>
                        )}
                    </Button>
                    
                    <Button
                        variant="outline"
                        onClick={() => {
                            setTask({
                                title: "",
                                content: "",
                                difficult_levels: 0,
                                user_id: 0
                            });
                            setChosenUser(undefined);
                            setMessage("");
                        }}
                        className="bg-zinc-800 border-zinc-700 text-zinc-300 hover:bg-zinc-700 hover:text-white"
                    >
                        <X className="w-4 h-4 mr-2" />
                        Wyczyść
                    </Button>
                </div>

                {/* Status Message */}
                {message && (
                    <div className={`flex items-center gap-2 p-3 rounded-lg ${
                        message.includes("task created") || message.includes("utworzone")
                            ? "bg-green-900/30 text-green-400 border border-green-800"
                            : "bg-red-900/30 text-red-400 border border-red-800"
                    }`}>
                        <AlertCircle className="w-4 h-4" />
                        {message}
                    </div>
                )}

                {/* Task Preview */}
                {(task.title || task.content || chosenUser) && (
                    <Card className="bg-zinc-800 border-zinc-700">
                        <CardHeader>
                            <CardTitle className="text-white text-sm">Podgląd zadania</CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-2 text-sm">
                            {task.title && (
                                <div>
                                    <span className="text-zinc-400">Tytuł:</span>
                                    <span className="text-white ml-2">{task.title}</span>
                                </div>
                            )}
                            {chosenUser && (
                                <div>
                                    <span className="text-zinc-400">Przypisane do:</span>
                                    <span className="text-white ml-2">{chosenUser.firstName} {chosenUser.lastName}</span>
                                </div>
                            )}
                            {task.difficult_levels > 0 && (
                                <div>
                                    <span className="text-zinc-400">Trudność:</span>
                                    <Badge 
                                        variant="outline" 
                                        className={`ml-2 ${getDifficultyColor(task.difficult_levels)}`}
                                    >
                                        {getDifficultyText(task.difficult_levels)}
                                    </Badge>
                                </div>
                            )}
                        </CardContent>
                    </Card>
                )}
            </CardContent>
        </Card>
    );
}