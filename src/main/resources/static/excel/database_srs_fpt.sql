USE [master]
GO
/****** Object:  Database [TracNghiemOnline]    Script Date: 15-Mar-21 3:26:10 PM ******/
CREATE DATABASE [TracNghiemOnline]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'FPTTracNghiemOnline', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\FPTTracNghiemOnline.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'FPTTracNghiemOnline_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\FPTTracNghiemOnline_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [TracNghiemOnline] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TracNghiemOnline].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TracNghiemOnline] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET ARITHABORT OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [TracNghiemOnline] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [TracNghiemOnline] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET  DISABLE_BROKER 
GO
ALTER DATABASE [TracNghiemOnline] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [TracNghiemOnline] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [TracNghiemOnline] SET  MULTI_USER 
GO
ALTER DATABASE [TracNghiemOnline] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [TracNghiemOnline] SET DB_CHAINING OFF 
GO
ALTER DATABASE [TracNghiemOnline] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [TracNghiemOnline] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [TracNghiemOnline] SET DELAYED_DURABILITY = DISABLED 
GO
USE [TracNghiemOnline]
GO
/****** Object:  Table [dbo].[ANH]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ANH](
	[id_anh] [int] NOT NULL,
	[url] [nvarchar](50) NULL,
	[id_cau_hoi] [int] NULL,
 CONSTRAINT [PK_ANH] PRIMARY KEY CLUSTERED 
(
	[id_anh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BODETHI]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BODETHI](
	[id_bo_de] [int] NOT NULL,
	[ten_de_thi] [nvarchar](50) NULL,
	[id_ngan_hang_de] [int] NULL,
	[id_lop] [int] NULL,
	[id_mon_hoc] [int] NULL,
 CONSTRAINT [PK_BODETHI] PRIMARY KEY CLUSTERED 
(
	[id_bo_de] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CAUHOI]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CAUHOI](
	[id_cau_hoi] [int] NOT NULL,
	[dap_an] [nvarchar](50) NULL,
	[ma_de] [nvarchar](50) NULL,
	[id_bo_de] [int] NULL,
	[giai_thich] [nvarchar](50) NULL,
 CONSTRAINT [PK_CAUHOI] PRIMARY KEY CLUSTERED 
(
	[id_cau_hoi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LOP]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LOP](
	[id_lop] [int] NOT NULL,
	[ten_lop] [nvarchar](50) NULL,
 CONSTRAINT [PK_LOP] PRIMARY KEY CLUSTERED 
(
	[id_lop] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[MONHOC]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MONHOC](
	[id_mon_hoc] [int] NOT NULL,
	[ten_mon_hoc] [nvarchar](50) NULL,
 CONSTRAINT [PK_MONHOC] PRIMARY KEY CLUSTERED 
(
	[id_mon_hoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NGANHANGDE]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NGANHANGDE](
	[id_ngan_hang_de] [int] NOT NULL,
	[ten_ngan_hang_de] [nvarchar](50) NULL,
 CONSTRAINT [PK_NGANHANGDE] PRIMARY KEY CLUSTERED 
(
	[id_ngan_hang_de] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PHUONGAN]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHUONGAN](
	[id_phuong_an] [int] NOT NULL,
	[noidun] [nvarchar](50) NULL,
	[id_cau_hoi] [int] NULL,
	[iscorrect] [bit] NULL,
 CONSTRAINT [PK_PHUONGAN] PRIMARY KEY CLUSTERED 
(
	[id_phuong_an] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ROLE]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ROLE](
	[role_id] [int] NOT NULL,
	[role_name] [nvarchar](50) NULL,
 CONSTRAINT [PK_ROLE] PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TAIKHOAN]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TAIKHOAN](
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[ho_va_ten] [nvarchar](50) NULL,
	[email] [nvarchar](50) NULL,
	[dia_chi] [nvarchar](50) NULL,
	[so_dien_thoai] [nvarchar](50) NULL,
	[url_avatar] [nvarchar](50) NULL,
	[role_id] [int] NULL,
	[date_of_birth] [date] NULL,
	[enable] [bit] NULL,
 CONSTRAINT [PK_TAIKHOAN] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[THI]    Script Date: 15-Mar-21 3:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[THI](
	[username] [nvarchar](50) NOT NULL,
	[id_bo_de] [int] NULL,
	[ngay_gio_bat_dau] [date] NULL,
	[ngay_gio_ket_thuc] [date] NULL,
	[tong_diem] [int] NULL,
 CONSTRAINT [PK_THI] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[ANH]  WITH CHECK ADD  CONSTRAINT [FK_ANH_CAUHOI] FOREIGN KEY([id_cau_hoi])
REFERENCES [dbo].[CAUHOI] ([id_cau_hoi])
GO
ALTER TABLE [dbo].[ANH] CHECK CONSTRAINT [FK_ANH_CAUHOI]
GO
ALTER TABLE [dbo].[BODETHI]  WITH CHECK ADD  CONSTRAINT [FK_BODETHI_LOP] FOREIGN KEY([id_lop])
REFERENCES [dbo].[LOP] ([id_lop])
GO
ALTER TABLE [dbo].[BODETHI] CHECK CONSTRAINT [FK_BODETHI_LOP]
GO
ALTER TABLE [dbo].[BODETHI]  WITH CHECK ADD  CONSTRAINT [FK_BODETHI_MONHOC] FOREIGN KEY([id_mon_hoc])
REFERENCES [dbo].[MONHOC] ([id_mon_hoc])
GO
ALTER TABLE [dbo].[BODETHI] CHECK CONSTRAINT [FK_BODETHI_MONHOC]
GO
ALTER TABLE [dbo].[BODETHI]  WITH CHECK ADD  CONSTRAINT [FK_BODETHI_NGANHANGDE] FOREIGN KEY([id_ngan_hang_de])
REFERENCES [dbo].[NGANHANGDE] ([id_ngan_hang_de])
GO
ALTER TABLE [dbo].[BODETHI] CHECK CONSTRAINT [FK_BODETHI_NGANHANGDE]
GO
ALTER TABLE [dbo].[CAUHOI]  WITH CHECK ADD  CONSTRAINT [FK_CAUHOI_BODETHI] FOREIGN KEY([id_bo_de])
REFERENCES [dbo].[BODETHI] ([id_bo_de])
GO
ALTER TABLE [dbo].[CAUHOI] CHECK CONSTRAINT [FK_CAUHOI_BODETHI]
GO
ALTER TABLE [dbo].[PHUONGAN]  WITH CHECK ADD  CONSTRAINT [FK_PHUONGAN_CAUHOI] FOREIGN KEY([id_cau_hoi])
REFERENCES [dbo].[CAUHOI] ([id_cau_hoi])
GO
ALTER TABLE [dbo].[PHUONGAN] CHECK CONSTRAINT [FK_PHUONGAN_CAUHOI]
GO
ALTER TABLE [dbo].[TAIKHOAN]  WITH CHECK ADD  CONSTRAINT [FK_TAIKHOAN_ROLE] FOREIGN KEY([role_id])
REFERENCES [dbo].[ROLE] ([role_id])
GO
ALTER TABLE [dbo].[TAIKHOAN] CHECK CONSTRAINT [FK_TAIKHOAN_ROLE]
GO
ALTER TABLE [dbo].[THI]  WITH CHECK ADD  CONSTRAINT [FK_THI_BODETHI] FOREIGN KEY([id_bo_de])
REFERENCES [dbo].[BODETHI] ([id_bo_de])
GO
ALTER TABLE [dbo].[THI] CHECK CONSTRAINT [FK_THI_BODETHI]
GO
ALTER TABLE [dbo].[THI]  WITH CHECK ADD  CONSTRAINT [FK_THI_TAIKHOAN] FOREIGN KEY([username])
REFERENCES [dbo].[TAIKHOAN] ([username])
GO
ALTER TABLE [dbo].[THI] CHECK CONSTRAINT [FK_THI_TAIKHOAN]
GO
USE [master]
GO
ALTER DATABASE [TracNghiemOnline] SET  READ_WRITE 
GO
