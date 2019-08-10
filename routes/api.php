<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

//Login Route
Route::post('login', 'UserController@login');

//Register Route
Route::post('register', 'UserController@register');

//Get Details of user
Route::group(['middleware' => 'auth:api'], function(){
	Route::post('details', 'UserController@details');
});

//Retrieve list of classes
Route::get('/classes', 'StandardController@index')->name('classes');

//Retrieve list of Subjects
Route::get('/subjects/{class_id}', 'SubjectController@index')->name('subjects');

//Retrieve list of Chapters or Modules
Route::get('/modules/{subject_id}', 'ModulesController@index')->name('modules');

//Retrieve list of Chapters or Modules
Route::get('/files/{module_id}/lang/{lang}', 'FilesController@index')->name('files');