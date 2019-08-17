<?php

namespace App\Http\Controllers;
use App\Classes;
use Illuminate\Http\Request;
use Response;

class StandardController extends Controller
{
    //Retrieve Classes
    public function index()
    {
        $classes = Classes::all()->toArray();
        $toSend = [];
        if(count($classes)!=0)
        {
        	$toSend = array(
        		"success" => true,
        		"data" => $classes,
        		"message" => "Tutorials available for above classes"
        	);
        }
        else{
        	$toSend = array(
        		"success" => false,
        		"data" => $classes,
        		"message" => "Nothing retrieved"
        	);
        }
        return Response::json($toSend);
    }
}
