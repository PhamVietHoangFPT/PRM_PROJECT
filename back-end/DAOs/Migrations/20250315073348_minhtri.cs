﻿using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DAOs.Migrations
{
    /// <inheritdoc />
    public partial class minhtri : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "accessory_type",
                columns: table => new
                {
                    accessory_type_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    processing_price = table.Column<double>(type: "float", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_accessory_type", x => x.accessory_type_id);
                });

            migrationBuilder.CreateTable(
                name: "diamond_price",
                columns: table => new
                {
                    diamond_price_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    eff_time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    min_carat_eff = table.Column<float>(type: "real", nullable: false),
                    max_carat_eff = table.Column<float>(type: "real", nullable: false),
                    color = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    clarity = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    unit_price = table.Column<double>(type: "float", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_diamond_price", x => x.diamond_price_id);
                });

            migrationBuilder.CreateTable(
                name: "material_price",
                columns: table => new
                {
                    material_price_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    karat = table.Column<int>(type: "int", nullable: false),
                    eff_time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    unit_price = table.Column<double>(type: "float", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_material_price", x => x.material_price_id);
                });

            migrationBuilder.CreateTable(
                name: "merchant",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(250)", nullable: false),
                    MerchantName = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    MerchantWebLink = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    MerchentIpnUrl = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    MerchantReturnUrl = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    SecretKey = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    IsActive = table.Column<bool>(type: "bit", nullable: false),
                    CreatedBy = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "datetime2", nullable: true),
                    LastUpdatedBy = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    LastUpdateAt = table.Column<DateTime>(type: "datetime2", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_merchant", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "payment_destination",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(250)", nullable: false),
                    DesLogo = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    DesShortName = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    DesName = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    DesSortIndex = table.Column<int>(type: "int", nullable: true),
                    DesParentId = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    IsActive = table.Column<bool>(type: "bit", nullable: true),
                    CreatedBy = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "datetime2", nullable: true),
                    LastUpdatedBy = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    LastUpdateAt = table.Column<DateTime>(type: "datetime2", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_payment_destination", x => x.Id);
                    table.ForeignKey(
                        name: "FK_payment_destination_payment_destination_DesParentId",
                        column: x => x.DesParentId,
                        principalTable: "payment_destination",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "promotion",
                columns: table => new
                {
                    promotion_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    promotion_name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    promotion_code = table.Column<string>(type: "nvarchar(10)", maxLength: 10, nullable: false),
                    discount_percent = table.Column<float>(type: "real", nullable: false),
                    start_time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    end_time = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_promotion", x => x.promotion_id);
                });

            migrationBuilder.CreateTable(
                name: "rank",
                columns: table => new
                {
                    rank_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    rank_name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    discount = table.Column<float>(type: "real", nullable: false),
                    reward_point = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_rank", x => x.rank_id);
                });

            migrationBuilder.CreateTable(
                name: "shape",
                columns: table => new
                {
                    shape_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shape", x => x.shape_id);
                });

            migrationBuilder.CreateTable(
                name: "payment",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    PaymentContent = table.Column<string>(type: "nvarchar(250)", nullable: false),
                    PaymentCurrency = table.Column<string>(type: "nvarchar(250)", nullable: false),
                    PaymentRefId = table.Column<string>(type: "nvarchar(250)", nullable: false),
                    RequiredAmount = table.Column<decimal>(type: "decimal(19,2)", nullable: true),
                    PaymentDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    ExpireDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    PaymentLanguage = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    MerchantId = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    PaymentDestinationId = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    PaidAmount = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    PaymentStatus = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    PaymentLastMessage = table.Column<string>(type: "nvarchar(250)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_payment", x => x.Id);
                    table.ForeignKey(
                        name: "FK_payment_merchant_MerchantId",
                        column: x => x.MerchantId,
                        principalTable: "merchant",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_payment_payment_destination_PaymentDestinationId",
                        column: x => x.PaymentDestinationId,
                        principalTable: "payment_destination",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "account",
                columns: table => new
                {
                    account_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    role = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    rank_id = table.Column<long>(type: "bigint", nullable: false),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    email = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    password = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    phone_number = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    address = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    birthday = table.Column<DateTime>(type: "datetime2", nullable: false),
                    gender = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    reward_point = table.Column<int>(type: "int", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_account", x => x.account_id);
                    table.ForeignKey(
                        name: "FK_account_rank_rank_id",
                        column: x => x.rank_id,
                        principalTable: "rank",
                        principalColumn: "rank_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "accessory",
                columns: table => new
                {
                    accessory_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    karat = table.Column<int>(type: "int", nullable: false),
                    material_weight = table.Column<float>(type: "real", nullable: false),
                    name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    quantity = table.Column<int>(type: "int", nullable: false),
                    accessory_type_id = table.Column<long>(type: "bigint", nullable: false),
                    shape_id = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_accessory", x => x.accessory_id);
                    table.ForeignKey(
                        name: "FK_accessory_accessory_type_accessory_type_id",
                        column: x => x.accessory_type_id,
                        principalTable: "accessory_type",
                        principalColumn: "accessory_type_id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_accessory_shape_shape_id",
                        column: x => x.shape_id,
                        principalTable: "shape",
                        principalColumn: "shape_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "diamond",
                columns: table => new
                {
                    diamond_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    lab = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    certificate_number = table.Column<long>(type: "bigint", nullable: false),
                    certificate_url = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    image_url = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    carat = table.Column<float>(type: "real", nullable: false),
                    cut = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    color = table.Column<int>(type: "int", nullable: false),
                    clarity = table.Column<int>(type: "int", nullable: false),
                    polish = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    symmetry = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    fluorescence = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    availability = table.Column<bool>(type: "bit", nullable: false),
                    shape_id = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_diamond", x => x.diamond_id);
                    table.ForeignKey(
                        name: "FK_diamond_shape_shape_id",
                        column: x => x.shape_id,
                        principalTable: "shape",
                        principalColumn: "shape_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "payment_notification",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    PaymentRefId = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    NotiContent = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiAmount = table.Column<decimal>(type: "decimal(19,2)", nullable: false),
                    NotiMessage = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiSignature = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiPaymentId = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiMerchantId = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    NotiStatus = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiResDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    NotiResMessage = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    NotiResHttpCode = table.Column<string>(type: "nvarchar(50)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_payment_notification", x => x.Id);
                    table.ForeignKey(
                        name: "FK_payment_notification_merchant_NotiMerchantId",
                        column: x => x.NotiMerchantId,
                        principalTable: "merchant",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_payment_notification_payment_NotiPaymentId",
                        column: x => x.NotiPaymentId,
                        principalTable: "payment",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "payment_signature",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    SignValue = table.Column<string>(type: "nvarchar(500)", nullable: true),
                    SignAlgo = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    SignDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    SignOwn = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    PaymentId = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    IsValid = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_payment_signature", x => x.Id);
                    table.ForeignKey(
                        name: "FK_payment_signature_payment_PaymentId",
                        column: x => x.PaymentId,
                        principalTable: "payment",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "blog",
                columns: table => new
                {
                    blog_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    author_id = table.Column<long>(type: "bigint", nullable: false),
                    title = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    content = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false),
                    is_hidden = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_blog", x => x.blog_id);
                    table.ForeignKey(
                        name: "FK_blog_account_author_id",
                        column: x => x.author_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "payment_transaction",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(50)", nullable: false),
                    TransMessage = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    TranPayload = table.Column<string>(type: "nvarchar(500)", nullable: true),
                    TranStatus = table.Column<string>(type: "nvarchar(10)", nullable: true),
                    TranAmount = table.Column<decimal>(type: "decimal(19,2)", nullable: true),
                    TranDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    PaymentId = table.Column<string>(type: "nvarchar(50)", nullable: true),
                    TranRefId = table.Column<string>(type: "nvarchar(250)", nullable: true),
                    AccountId = table.Column<long>(type: "bigint", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_payment_transaction", x => x.Id);
                    table.ForeignKey(
                        name: "FK_payment_transaction_account_AccountId",
                        column: x => x.AccountId,
                        principalTable: "account",
                        principalColumn: "account_id");
                    table.ForeignKey(
                        name: "FK_payment_transaction_payment_PaymentId",
                        column: x => x.PaymentId,
                        principalTable: "payment",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "price_rate",
                columns: table => new
                {
                    price_rate_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    account_id = table.Column<long>(type: "bigint", nullable: false),
                    percent = table.Column<float>(type: "real", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_price_rate", x => x.price_rate_id);
                    table.ForeignKey(
                        name: "FK_price_rate_account_account_id",
                        column: x => x.account_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "accessory_image",
                columns: table => new
                {
                    image_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    image_url = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    accessory_id = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_accessory_image", x => x.image_id);
                    table.ForeignKey(
                        name: "FK_accessory_image_accessory_accessory_id",
                        column: x => x.accessory_id,
                        principalTable: "accessory",
                        principalColumn: "accessory_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "order",
                columns: table => new
                {
                    order_id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    total_price = table.Column<double>(type: "float", nullable: false),
                    total_discount_percent = table.Column<float>(type: "real", nullable: false),
                    rank_id = table.Column<long>(type: "bigint", nullable: false),
                    price_rate_id = table.Column<long>(type: "bigint", nullable: false),
                    order_status = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    shipping_address = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    phone_number = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false),
                    customer_id = table.Column<long>(type: "bigint", nullable: false),
                    sale_staff_id = table.Column<long>(type: "bigint", nullable: true),
                    delivery_staff_id = table.Column<long>(type: "bigint", nullable: true),
                    promotion_id = table.Column<long>(type: "bigint", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_order", x => x.order_id);
                    table.ForeignKey(
                        name: "FK_order_account_customer_id",
                        column: x => x.customer_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_account_delivery_staff_id",
                        column: x => x.delivery_staff_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_account_sale_staff_id",
                        column: x => x.sale_staff_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_price_rate_price_rate_id",
                        column: x => x.price_rate_id,
                        principalTable: "price_rate",
                        principalColumn: "price_rate_id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_order_promotion_promotion_id",
                        column: x => x.promotion_id,
                        principalTable: "promotion",
                        principalColumn: "promotion_id");
                    table.ForeignKey(
                        name: "FK_order_rank_rank_id",
                        column: x => x.rank_id,
                        principalTable: "rank",
                        principalColumn: "rank_id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "order_detail",
                columns: table => new
                {
                    order_detail_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    size = table.Column<float>(type: "real", nullable: true),
                    item_price = table.Column<double>(type: "float", nullable: true),
                    order_id = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    diamond_id = table.Column<long>(type: "bigint", nullable: true),
                    accessory_id = table.Column<long>(type: "bigint", nullable: true),
                    diamond_price_id = table.Column<long>(type: "bigint", nullable: false),
                    material_price_id = table.Column<long>(type: "bigint", nullable: true),
                    feedback_id = table.Column<long>(type: "bigint", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_order_detail", x => x.order_detail_id);
                    table.ForeignKey(
                        name: "FK_order_detail_accessory_accessory_id",
                        column: x => x.accessory_id,
                        principalTable: "accessory",
                        principalColumn: "accessory_id");
                    table.ForeignKey(
                        name: "FK_order_detail_diamond_diamond_id",
                        column: x => x.diamond_id,
                        principalTable: "diamond",
                        principalColumn: "diamond_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_detail_diamond_price_diamond_price_id",
                        column: x => x.diamond_price_id,
                        principalTable: "diamond_price",
                        principalColumn: "diamond_price_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_detail_material_price_material_price_id",
                        column: x => x.material_price_id,
                        principalTable: "material_price",
                        principalColumn: "material_price_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_order_detail_order_order_id",
                        column: x => x.order_id,
                        principalTable: "order",
                        principalColumn: "order_id");
                });

            migrationBuilder.CreateTable(
                name: "transaction",
                columns: table => new
                {
                    transaction_id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    order_id = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    payment_method = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    amount = table.Column<double>(type: "float", nullable: false),
                    transaction_status = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_transaction", x => x.transaction_id);
                    table.ForeignKey(
                        name: "FK_transaction_order_order_id",
                        column: x => x.order_id,
                        principalTable: "order",
                        principalColumn: "order_id");
                });

            migrationBuilder.CreateTable(
                name: "feedback",
                columns: table => new
                {
                    feedback_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    customer_id = table.Column<long>(type: "bigint", nullable: true),
                    order_detail_id = table.Column<long>(type: "bigint", nullable: true),
                    accessory_id = table.Column<long>(type: "bigint", nullable: true),
                    score = table.Column<int>(type: "int", nullable: false),
                    created_at = table.Column<DateTime>(type: "datetime2", nullable: false),
                    content = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_feedback", x => x.feedback_id);
                    table.ForeignKey(
                        name: "FK_feedback_accessory_accessory_id",
                        column: x => x.accessory_id,
                        principalTable: "accessory",
                        principalColumn: "accessory_id");
                    table.ForeignKey(
                        name: "FK_feedback_account_customer_id",
                        column: x => x.customer_id,
                        principalTable: "account",
                        principalColumn: "account_id");
                    table.ForeignKey(
                        name: "FK_feedback_order_detail_order_detail_id",
                        column: x => x.order_detail_id,
                        principalTable: "order_detail",
                        principalColumn: "order_detail_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "warranty_card",
                columns: table => new
                {
                    warranty_card_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    order_detail_id = table.Column<long>(type: "bigint", nullable: false),
                    diamond_id = table.Column<long>(type: "bigint", nullable: true),
                    accessory_id = table.Column<long>(type: "bigint", nullable: true),
                    start_time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    end_time = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_warranty_card", x => x.warranty_card_id);
                    table.ForeignKey(
                        name: "FK_warranty_card_accessory_accessory_id",
                        column: x => x.accessory_id,
                        principalTable: "accessory",
                        principalColumn: "accessory_id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_warranty_card_diamond_diamond_id",
                        column: x => x.diamond_id,
                        principalTable: "diamond",
                        principalColumn: "diamond_id");
                    table.ForeignKey(
                        name: "FK_warranty_card_order_detail_order_detail_id",
                        column: x => x.order_detail_id,
                        principalTable: "order_detail",
                        principalColumn: "order_detail_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "warranty_request",
                columns: table => new
                {
                    warranty_request_id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    warranty_reason = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    customer_id = table.Column<long>(type: "bigint", nullable: false),
                    warranty_staff_id = table.Column<long>(type: "bigint", nullable: true),
                    delivery_staff_id = table.Column<long>(type: "bigint", nullable: true),
                    warranty_card_id = table.Column<long>(type: "bigint", nullable: false),
                    receive_time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    return_time = table.Column<DateTime>(type: "datetime2", nullable: true),
                    warranty_status = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    shipping_address = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    phone_number = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_warranty_request", x => x.warranty_request_id);
                    table.ForeignKey(
                        name: "FK_warranty_request_account_customer_id",
                        column: x => x.customer_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_warranty_request_account_delivery_staff_id",
                        column: x => x.delivery_staff_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_warranty_request_account_warranty_staff_id",
                        column: x => x.warranty_staff_id,
                        principalTable: "account",
                        principalColumn: "account_id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_warranty_request_warranty_card_warranty_card_id",
                        column: x => x.warranty_card_id,
                        principalTable: "warranty_card",
                        principalColumn: "warranty_card_id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_accessory_accessory_type_id",
                table: "accessory",
                column: "accessory_type_id");

            migrationBuilder.CreateIndex(
                name: "IX_accessory_shape_id",
                table: "accessory",
                column: "shape_id");

            migrationBuilder.CreateIndex(
                name: "IX_accessory_image_accessory_id",
                table: "accessory_image",
                column: "accessory_id");

            migrationBuilder.CreateIndex(
                name: "IX_account_rank_id",
                table: "account",
                column: "rank_id");

            migrationBuilder.CreateIndex(
                name: "IX_blog_author_id",
                table: "blog",
                column: "author_id");

            migrationBuilder.CreateIndex(
                name: "IX_diamond_shape_id",
                table: "diamond",
                column: "shape_id");

            migrationBuilder.CreateIndex(
                name: "IX_feedback_accessory_id",
                table: "feedback",
                column: "accessory_id");

            migrationBuilder.CreateIndex(
                name: "IX_feedback_customer_id",
                table: "feedback",
                column: "customer_id");

            migrationBuilder.CreateIndex(
                name: "IX_feedback_order_detail_id",
                table: "feedback",
                column: "order_detail_id",
                unique: true,
                filter: "[order_detail_id] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_order_customer_id",
                table: "order",
                column: "customer_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_delivery_staff_id",
                table: "order",
                column: "delivery_staff_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_price_rate_id",
                table: "order",
                column: "price_rate_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_promotion_id",
                table: "order",
                column: "promotion_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_rank_id",
                table: "order",
                column: "rank_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_sale_staff_id",
                table: "order",
                column: "sale_staff_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_detail_accessory_id",
                table: "order_detail",
                column: "accessory_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_detail_diamond_id",
                table: "order_detail",
                column: "diamond_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_detail_diamond_price_id",
                table: "order_detail",
                column: "diamond_price_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_detail_material_price_id",
                table: "order_detail",
                column: "material_price_id");

            migrationBuilder.CreateIndex(
                name: "IX_order_detail_order_id",
                table: "order_detail",
                column: "order_id");

            migrationBuilder.CreateIndex(
                name: "IX_payment_MerchantId",
                table: "payment",
                column: "MerchantId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_PaymentDestinationId",
                table: "payment",
                column: "PaymentDestinationId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_destination_DesParentId",
                table: "payment_destination",
                column: "DesParentId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_notification_NotiMerchantId",
                table: "payment_notification",
                column: "NotiMerchantId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_notification_NotiPaymentId",
                table: "payment_notification",
                column: "NotiPaymentId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_signature_PaymentId",
                table: "payment_signature",
                column: "PaymentId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_transaction_AccountId",
                table: "payment_transaction",
                column: "AccountId");

            migrationBuilder.CreateIndex(
                name: "IX_payment_transaction_PaymentId",
                table: "payment_transaction",
                column: "PaymentId");

            migrationBuilder.CreateIndex(
                name: "IX_price_rate_account_id",
                table: "price_rate",
                column: "account_id");

            migrationBuilder.CreateIndex(
                name: "IX_promotion_promotion_code",
                table: "promotion",
                column: "promotion_code",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_transaction_order_id",
                table: "transaction",
                column: "order_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_card_accessory_id",
                table: "warranty_card",
                column: "accessory_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_card_diamond_id",
                table: "warranty_card",
                column: "diamond_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_card_order_detail_id",
                table: "warranty_card",
                column: "order_detail_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_request_customer_id",
                table: "warranty_request",
                column: "customer_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_request_delivery_staff_id",
                table: "warranty_request",
                column: "delivery_staff_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_request_warranty_card_id",
                table: "warranty_request",
                column: "warranty_card_id");

            migrationBuilder.CreateIndex(
                name: "IX_warranty_request_warranty_staff_id",
                table: "warranty_request",
                column: "warranty_staff_id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "accessory_image");

            migrationBuilder.DropTable(
                name: "blog");

            migrationBuilder.DropTable(
                name: "feedback");

            migrationBuilder.DropTable(
                name: "payment_notification");

            migrationBuilder.DropTable(
                name: "payment_signature");

            migrationBuilder.DropTable(
                name: "payment_transaction");

            migrationBuilder.DropTable(
                name: "transaction");

            migrationBuilder.DropTable(
                name: "warranty_request");

            migrationBuilder.DropTable(
                name: "payment");

            migrationBuilder.DropTable(
                name: "warranty_card");

            migrationBuilder.DropTable(
                name: "merchant");

            migrationBuilder.DropTable(
                name: "payment_destination");

            migrationBuilder.DropTable(
                name: "order_detail");

            migrationBuilder.DropTable(
                name: "accessory");

            migrationBuilder.DropTable(
                name: "diamond");

            migrationBuilder.DropTable(
                name: "diamond_price");

            migrationBuilder.DropTable(
                name: "material_price");

            migrationBuilder.DropTable(
                name: "order");

            migrationBuilder.DropTable(
                name: "accessory_type");

            migrationBuilder.DropTable(
                name: "shape");

            migrationBuilder.DropTable(
                name: "price_rate");

            migrationBuilder.DropTable(
                name: "promotion");

            migrationBuilder.DropTable(
                name: "account");

            migrationBuilder.DropTable(
                name: "rank");
        }
    }
}
