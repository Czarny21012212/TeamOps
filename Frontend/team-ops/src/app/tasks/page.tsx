'use client';

import { Button } from "@/components/ui/button";
import Navbar from "../components/navbar/page";
import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

type user = {
    userId: number,
    firstName: String,
    lastName: String,
    email: String
}

type task = {
    "date": String,
    "difficultLevels": String,
    "isRead": boolean,
    "id": number,
    "title": String,
    "content": String,
    "status": String
    "userId": number
}

export default function Tasks(){

    const [userData, setUserData] = useState<user>({
        userId: 0,
        firstName: "",
        lastName: "",
        email: ""
    });

    const [tasks, setTasks] = useState<task[]>([]);

    
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
            console.log(data)
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        })
    }, []);

    useEffect(() => {
    const client = new Client({
        webSocketFactory: () => new SockJS("http://localhost:8081/ws"),
        reconnectDelay: 2000,
        debug: (str) => console.log(str),
    });

    client.onConnect = () => {
        client.subscribe("/topic/userTasks", (message) => {
            const data = JSON.parse(message.body);
            setTasks(data)
            console.log(data)
        });

       console.log("Wysyłam żądanie z nagłówkiem user-email:", userData.email);
        client.publish({
            destination: "/app/userTasks",
            headers: {
                "user-email": String(userData.email)
            }
    });
    }

    client.activate();

    return () => {
        client.deactivate().catch((err) => console.error("❌ Disconnect error:", err));
    };
    }, [userData.email]);

    const [displayReceivedTasks, setDisplayReceivedTasks] = useState<Boolean>(true);
    const [displaySentTasks, setDisplaySentTasks] = useState<Boolean>(false);

    return(
        <div className="flex min-h-screen bg-black text-white">
        {/* Sidebar (Navbar) */}
        <div className="w-[150px] bg-black p-4">
            <Navbar activeRoute="/tasks" />
        </div>
        {/* Main Content */}
        <div className="flex-2 p-4">
            <div className="w-full max-w-2xl mx-auto">
            <div className="flex items-center gap-5">
                <h1 className="text-4xl font-semibold mb-3">Zadania</h1>
                <div className="flex gap-5 ">
                    <Button
                    onClick={() => {
                        setDisplayReceivedTasks(true);
                        setDisplaySentTasks(false);
                    }}
                    >
                    Odebrane
                    </Button>
                    <Button
                    onClick={() => {
                        setDisplayReceivedTasks(false);
                        setDisplaySentTasks(true);
                    }}
                    >
                    Wysłane
                    </Button>
                </div>
            </div>
            {/* Tasks Div (Centered Below Main Content) */}
            <div className="mt-6 w-full">
                {displayReceivedTasks && (
                <div className=" bg-gray-900 p-4 rounded-lg">
                    {tasks.map((task) => (
                    <div key={task.id} className="m-10">
                        <p>{task.content}</p>
                        <p>{task.date}</p>
                        <p>{task.difficultLevels}</p>
                        <p>{task.id}</p>
                        <p>{task.isRead}</p>
                        <p>{task.status}</p>
                        <p>{task.title}</p>
                    </div>
                ))}
                </div>
                )}
                {displaySentTasks && (
                <div className="text-center bg-gray-900 p-4 rounded-lg">
                    Lista zadań wysłanych
                </div>
                )}
            </div>
            </div>
        </div>
        </div>
    )
}