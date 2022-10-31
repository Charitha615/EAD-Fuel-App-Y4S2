using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDBTestProject.Auth;
using MongoDBTestProject.Model;
using MongoDBTestProject.Service;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.Configure<DatabaseSettings>(builder.Configuration.GetSection(nameof(DatabaseSettings)));
builder.Services.AddSingleton<IDatabaseSettings>(sp=>sp.GetRequiredService<IOptions <DatabaseSettings>>().Value);
builder.Services.AddSingleton<IMongoClient>(s => new MongoClient(builder.Configuration.GetValue<string>("DatabaseSettings:ConnectionString")));
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IAuthHashService, AuthHashService>();
builder.Services.AddScoped<IFuelStationService, FuelStationService>();

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
