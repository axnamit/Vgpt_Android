<?php

namespace App\Http\Controllers;
use App\Subjects;
use Illuminate\Http\Request;
use Response;

class SubjectController extends Controller
{
    //Retrieve Subjects for Classes
    public function index($class_id)
    {
        $subjects = Subjects::where('class_id', $class_id)->get()->toArray();
        $toSend = [];
        if(count($subjects)!=0)
        {
        	$toSend = array(
        		"success" => true,
        		"data" => $subjects,
        		"message" => "Subjects availbale for selected class"
        	);
        }
        else{
        	$toSend = array(
        		"success" => false,
        		"data" => $subjects,
        		"message" => "Nothing recieved"
        	);
        }
        return Response::json($toSend);
    }
}
