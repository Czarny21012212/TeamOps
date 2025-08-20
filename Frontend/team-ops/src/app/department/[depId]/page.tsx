"use client";

import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Users, Mail, CheckCircle, User, ArrowLeft, UserPlus } from "lucide-react";
import TaskCreator from '../../components/task-creator/page'
import { error } from "console";

export default function Department() {
  const params = useParams();
  const depId = params.depId;
  const router = useRouter();

    type usersData = {
        firstName?: string;
        lastName?: string;
        email?: string;
        finishedTasks?: number;
    };

  const [usersData, setUsersData] = useState<usersData[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const [isLeader, setIsLeader] = useState(false)
  const [createTask, setCreateTask] = useState(false)

  useEffect(() => {
    fetch(`http://localhost:8081/api/isLeader/${depId}`, {
      method: "Get",
      credentials: "include"
    })
    .then(response =>{
      if(response.ok){
        return response.json()
      }else{
         throw new Error("Network response was not ok");
      }
    })
    .then(data => {
      setIsLeader(data)
      console.log("Is Ledaer: " + data)
    })
    .catch(error => {
          console.error("There was a problem with the fetch operation:", error);
    })
  
  }, [depId])

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
    <div className="min-h-screen bg-black p-4">
      <div className="max-w-6xl mx-auto space-y-6">
        {/* Header */}
        <div className="flex items-center justify-between py-6">
          <div className="flex items-center gap-4">
            <Button
              variant="outline"
              size="sm"
              className="fixed left-10 top-10 bg-zinc-800 border-zinc-700 text-zinc-300 hover:bg-zinc-700 hover:text-white "
              onClick={() => router.back()}
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Powrót
            </Button>
            <div>
              <h1 className="text-3xl font-bold text-white">
                Dział {depId}
              </h1>
              <p className="text-zinc-400 mt-1">
                Zarządzaj zespołem i monitoruj postępy
              </p>
            </div>
            
          </div>
          <div>
            { isLeader && <Button onClick={() => setCreateTask(!createTask)}>Create task</Button>}  
          </div>
        </div>
        {createTask && isLeader && (<TaskCreator depId={Number(depId)} />)}

        {/* Stats Overview */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <Card className="bg-zinc-900 border-zinc-800">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-zinc-400 text-sm">Liczba pracowników</p>
                  <p className="text-2xl font-bold text-white">{usersData.length}</p>
                </div>
                <Users className="w-8 h-8 text-blue-500" />
              </div>
            </CardContent>
          </Card>
          
          <Card className="bg-zinc-900 border-zinc-800">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-zinc-400 text-sm">Ukończone zadania</p>
                  <p className="text-2xl font-bold text-white">
                    {usersData.reduce((total, user) => total + (user.finishedTasks || 0), 0)}
                  </p>
                </div>
                <CheckCircle className="w-8 h-8 text-green-500" />
              </div>
            </CardContent>
          </Card>
          
          <Card className="bg-zinc-900 border-zinc-800">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-zinc-400 text-sm">Średnia zadań</p>
                  <p className="text-2xl font-bold text-white">
                    {usersData.length > 0 
                      ? Math.round(usersData.reduce((total, user) => total + (user.finishedTasks || 0), 0) / usersData.length)
                      : 0
                    }
                  </p>
                </div>
                <CheckCircle className="w-8 h-8 text-yellow-500" />
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Team Members */}
        <Card className="bg-zinc-900 border-zinc-800">
          <CardHeader>
            <CardTitle className="text-white flex items-center gap-2">
              <Users className="w-5 h-5" />
              Członkowie zespołu
            </CardTitle>
            <CardDescription className="text-zinc-400">
              Lista wszystkich pracowników w dziale
            </CardDescription>
          </CardHeader>
          <CardContent>
            {usersData.length === 0 ? (
              <div className="text-center py-8">
                <Users className="w-16 h-16 text-zinc-600 mx-auto mb-4" />
                <h3 className="text-lg font-medium text-zinc-300 mb-2">Brak pracowników</h3>
                <p className="text-zinc-500 mb-4">Ten dział nie ma jeszcze przypisanych pracowników.</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {usersData.map((userData, index) => (
                  <Card key={index} className="bg-zinc-800 border-zinc-700 hover:bg-zinc-700/50 transition-colors">
                    <CardContent className="p-4">
                      
                      
                      <div className="space-y-2 text-sm">
                       <div className="flex justify-between">
                        <p className="text-white">{userData.firstName} {userData.lastName}</p>
                        <p className="text-white">{userData.email}</p>
                       </div>
                        <div className="flex justify-between items-center">
                          <span className="text-zinc-400">Ukończone zadania:</span>
                          <span className="text-zinc-300 font-medium">
                            {userData.finishedTasks || 0}
                          </span>
                        </div>
                        
                      </div>

                      <div className="mt-4 pt-3 border-t border-zinc-700">
                        <div className="flex gap-2">
                          <Button 
                            size="sm" 
                            className="flex-1 bg-zinc-700 text-white hover:bg-zinc-600 text-xs"
                          >
                            Zobacz profil
                          </Button>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}